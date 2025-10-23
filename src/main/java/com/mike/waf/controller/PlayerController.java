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

}
