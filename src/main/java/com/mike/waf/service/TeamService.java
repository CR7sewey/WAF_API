package com.mike.waf.service;

import com.mike.waf.model.entities.Team;
import com.mike.waf.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Optional<Team> findById(UUID id) {
        return teamRepository.findById(id);
    }
}
