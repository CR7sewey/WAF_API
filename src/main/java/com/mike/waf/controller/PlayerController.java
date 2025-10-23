package com.mike.waf.controller;

import com.mike.waf.model.DTO.PlayerRegisterDTO;
import com.mike.waf.model.entities.Player;
import com.mike.waf.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping // 201 or 422
    public ResponseEntity<Object> save(@RequestBody @Valid PlayerRegisterDTO playerRegisterDTO) {

            Player p = new Player();
            p.setName(playerRegisterDTO.name());
            p.setAge(BigInteger.valueOf(playerRegisterDTO.age()));
            p.setFavoritePosition(playerRegisterDTO.favoritePosition());
            p.setFavoriteTeam(playerRegisterDTO.favoriteTeam());
            p.setFavoritePlayer(playerRegisterDTO.favoritePlayer());
            p.setSkill(playerRegisterDTO.skill());
            var pl = playerService.save(p);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(pl.getId());
            return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();

    }

    @GetMapping(value = "/{id}") // 200 or 404
    public ResponseEntity<Player> findById(@PathVariable String id) {

            var uuid = UUID.fromString(id);
            return playerService.findById(uuid)
                    .map(player -> ResponseEntity.ok().body(player))
                    .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Page<Player>> findAllByNameAndRating(
            @RequestParam(required = false) String name, @RequestParam(required = false) Integer rating,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {

        Page<Player> players = playerService.findAllByNameAndRating(name, rating,  pageNumber, pageSize);
        return ResponseEntity.ok().body(players);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid PlayerRegisterDTO playerRegisterDTO) {

        UUID uuid = UUID.fromString(id);
        Optional<Player> p = playerService.findById(uuid);
        if (p.isPresent()) {
            var player = p.get();
            player.setName(playerRegisterDTO.name());
            player.setAge(BigInteger.valueOf(playerRegisterDTO.age()));
            player.setFavoritePosition(playerRegisterDTO.favoritePosition());
            player.setFavoriteTeam(playerRegisterDTO.favoriteTeam());
            player.setFavoritePlayer(playerRegisterDTO.favoritePlayer());
            player.setSkill(playerRegisterDTO.skill());
            playerService.update(player);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {

        UUID uuid = UUID.fromString(id);
        return playerService.findById(uuid).map(p -> {
            playerService.delete(p);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
