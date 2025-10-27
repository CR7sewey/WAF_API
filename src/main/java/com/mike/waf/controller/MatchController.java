package com.mike.waf.controller;

import com.mike.waf.model.DTO.MatchRegisterDTO;
import com.mike.waf.model.entities.Match;
import com.mike.waf.model.entities.Team;
import com.mike.waf.service.MatchService;
import com.mike.waf.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody MatchRegisterDTO matchRegisterDTO) {

        Match match = new Match();
        match.setResult(matchRegisterDTO.result());
        match.setDate(matchRegisterDTO.date());
        match.setStatus(matchRegisterDTO.status());
        match.setTeam1(matchRegisterDTO.team1());
        match.setTeam2(matchRegisterDTO.team2());

        var m = matchService.save(match);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(m.getId());
        return ResponseEntity.created(location).build();


    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> findById(@PathVariable String id) {

        UUID uuid = UUID.fromString(id);
        Optional<Match> match = matchService.findById(uuid);
        if (match.isPresent()) {
            return ResponseEntity.ok().body(match.get());
        }
        return ResponseEntity.notFound().build();

    }

}
