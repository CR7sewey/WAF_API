package com.mike.waf.controller;

import com.mike.waf.exceptions.DuplicateRegister;
import com.mike.waf.model.DTO.PlayerRegisterDTO;
import com.mike.waf.model.DTO.PlayerStatisticsDTO;
import com.mike.waf.model.DTO.TeamDTO;
import com.mike.waf.model.entities.Player;
import com.mike.waf.model.entities.Team;
import com.mike.waf.repository.TeamRepository;
import com.mike.waf.service.PlayerService;
import com.mike.waf.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid TeamDTO teamDTO) {
        var team = new Team();
        team.setName(teamDTO.name());
        team.setSize(teamDTO.size());
        var t = teamService.save(team);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(t.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> findById(@PathVariable String id) {
        var uuid = UUID.fromString(id);
        return teamService.findById(uuid).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid TeamDTO teamDTO) {

        UUID uuid = UUID.fromString(id);
        Optional<Team> t = teamService.findById(uuid);
        if (t.isPresent()) {
            var team = t.get();
            team.setName(teamDTO.name());
            team.setSize(teamDTO.size());
            teamService.update(team);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {

        UUID uuid = UUID.fromString(id);
        return teamService.findById(uuid).map(p -> {
            teamService.delete(p);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Team>> findAllByNameAndRating(
            @RequestParam(required = false) String name, @RequestParam(required = false) Integer rating,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {

        Page<Team> players = teamService.findAllByNameAndRating(name, rating,  pageNumber, pageSize);
        return ResponseEntity.ok().body(players);
    }

    @PutMapping("/{id}/addPlayer")
    public ResponseEntity<Object> addPlayers(@PathVariable String id, @RequestBody Player player) {

        // team
        UUID uuid = UUID.fromString(id);
        Optional<Team> t = teamService.findById(uuid);

        // player
        UUID playerId = UUID.fromString(String.valueOf(player.getId()));
        Optional<Player> p = playerService.findById(playerId);


        if (t.isPresent() && p.isPresent()) {

            var playerAlreadyAdded = t.get().getPlayers().stream().filter(p1 -> p1.getId().equals(playerId)).findFirst();
            if (playerAlreadyAdded.isPresent()) {
                throw new DuplicateRegister("Player already added");
            }

            var team = t.get();
            var playerFound = p.get();
            team.getPlayers().add(playerFound);
            teamService.update(team);

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}/removePlayer")
    public ResponseEntity<Object> removePlayers(@PathVariable String id, @RequestBody Player player) {

        // team
        UUID uuid = UUID.fromString(id);
        Optional<Team> t = teamService.findById(uuid);

        // player
        UUID playerId = UUID.fromString(String.valueOf(player.getId()));
        Optional<Player> p = playerService.findById(playerId);


        if (t.isPresent() && p.isPresent()) {

            var playerAlreadyAdded = t.get().getPlayers().stream().filter(p1 -> p1.getId().equals(playerId)).findFirst();
            if (playerAlreadyAdded.isEmpty()) {
                throw new DuplicateRegister("Player is not present");
            }

            var team = t.get();
            var playerFound = p.get();
            team.getPlayers().remove(playerFound);
            teamService.update(team);

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

}
