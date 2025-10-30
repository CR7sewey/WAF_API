package com.mike.waf.controller;

import com.mike.waf.exceptions.FieldsValidator;
import com.mike.waf.model.DTO.MatchRegisterDTO;
import com.mike.waf.model.DTO.PitchRegisterDTO;
import com.mike.waf.model.entities.Match;
import com.mike.waf.model.entities.Pitch;
import com.mike.waf.model.entities.Team;
import com.mike.waf.service.MatchService;
import com.mike.waf.service.PitchService;
import com.mike.waf.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final PitchService pitchService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid MatchRegisterDTO matchRegisterDTO) {

        Match match = new Match();
        match.setResult(matchRegisterDTO.result());
        match.setDate(matchRegisterDTO.date());
        match.setStatus(matchRegisterDTO.status());
        match.setTeam1(matchRegisterDTO.team1());
        match.setTeam2(matchRegisterDTO.team2());

        Optional<Pitch> pitchOptional = pitchService.findById(matchRegisterDTO.pitchId());
        if (pitchOptional.isEmpty()) {
            throw new FieldsValidator("Pitch not found", "pitchId");
        }

        match.setPitch(pitchOptional.get());


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

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid MatchRegisterDTO matchRegisterDTO) {
        UUID uuid = UUID.fromString(id);
        Optional<Match> mat = matchService.findById(uuid);
        if (mat.isPresent()) {
            Match match = mat.get();
            match.setResult(matchRegisterDTO.result());
            match.setDate(matchRegisterDTO.date());
            match.setStatus(matchRegisterDTO.status());
            if (!match.getTeam1().equals(matchRegisterDTO.team1())) {
                matchService.matchValidatorTeamAlreadyAssinedUpdate(matchRegisterDTO.team1(), matchRegisterDTO.team2(), 1);
            }
            if (!match.getTeam2().equals(matchRegisterDTO.team2())) {
                matchService.matchValidatorTeamAlreadyAssinedUpdate(matchRegisterDTO.team1(), matchRegisterDTO.team2(), 2);
            }
            match.setTeam1(matchRegisterDTO.team1());
            match.setTeam2(matchRegisterDTO.team2());

            Optional<Pitch> pitchOptional = pitchService.findById(matchRegisterDTO.pitchId());
            if (pitchOptional.isEmpty()) {
                throw new FieldsValidator("Pitch not found", "pitchId");
            }

            match.setPitch(pitchOptional.get());

            matchService.update(match);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Match> match = matchService.findById(uuid);
        if (match.isPresent()) {
            matchService.delete(match.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<Match>> findAll(
            @RequestParam(required = false) String pitchName,
            @RequestParam(required = false) String team1,
            @RequestParam(required = false) String team2,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {



        Page<Match> match =  matchService.findAll(
                pitchName,
                team1,
                team2,
                pageNumber,
                pageSize
        );

        return ResponseEntity.ok().body(match);
    }

}
