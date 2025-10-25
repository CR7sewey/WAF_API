package com.mike.waf.controller;

import com.mike.waf.model.DTO.TeamDTO;
import com.mike.waf.model.entities.Team;
import com.mike.waf.repository.TeamRepository;
import com.mike.waf.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody TeamDTO teamDTO) {
        var team = new Team();
        team.setName(teamDTO.name());
        var t = teamService.save(team);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(t.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> findById(@PathVariable String id) {
        var uuid = UUID.fromString(id);
        return teamService.findById(uuid).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
