package com.mike.waf.service;

import com.mike.waf.exceptions.FieldsValidator;
import com.mike.waf.exceptions.NotFoundFieldsValidator;
import com.mike.waf.model.DTO.MatchRegisterDTO;
import com.mike.waf.model.entities.Match;
import com.mike.waf.model.entities.Team;
import com.mike.waf.repository.MatchRepository;
import com.mike.waf.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService implements IService<Match> {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Override
    public Match save(Match match) {
        matchValidatorTeamExists(match);
        matchValidatorTeamAlreadyAssigned(match);
        return matchRepository.save(match);
    }

    @Override
    public void update(Match match) {
        if (match.getId() == null) {
            throw new IllegalArgumentException("Match id must not be null");
        }
        matchValidatorTeamExists(match);
        matchRepository.save(match);
    }

    @Override
    public void delete(Match match) {
        matchRepository.delete(match);
    }

    @Override
    public Optional<Match> findById(UUID id) {
        var found = matchRepository.findById(id);
        //var c = found.get();
        return found;
    }

    private void matchValidatorTeamExists(Match match) {
        if (match.getTeam1() != null) {
            var team1 = teamRepository.findById(match.getTeam1());
            if (team1.isEmpty()) {
                throw new NotFoundFieldsValidator("Team 1 Not Found", "team1");
            }
        }
        if (match.getTeam2() != null) {
            var team2 = teamRepository.findById(match.getTeam2());
            if (team2.isEmpty()) {
                throw new NotFoundFieldsValidator("Team 2 Not Found", "team2");
            }
        }
    }

    public void matchValidatorTeamAlreadyAssigned(Match match) {

        Set<UUID> teamsList1 = matchRepository.findAll()
                .stream().map(match1 -> match1.getTeam1()).collect(Collectors.toSet());
        Set<UUID> teamsList2 = matchRepository.findAll()
                .stream().map(match1 -> match1.getTeam2()).collect(Collectors.toSet());
        if (teamsList1.contains(match.getTeam1())
                || teamsList2.contains(match.getTeam1())
        ) {
            throw new FieldsValidator("Team 1 is already assigned to a match", "team1");
        }
        if (teamsList1.contains(match.getTeam2())
                || teamsList2.contains(match.getTeam2())
        ) {
            throw new FieldsValidator("Team 2 is already assigned to a match", "team2");
        }

    }

    public void matchValidatorTeamAlreadyAssinedUpdate(UUID team1, UUID team2, Integer teamNum) {
        Set<UUID> teamsList1 = matchRepository.findAll()
                .stream().map(match1 -> match1.getTeam1()).collect(Collectors.toSet());
        Set<UUID> teamsList2 = matchRepository.findAll()
                .stream().map(match1 -> match1.getTeam2()).collect(Collectors.toSet());
        if (teamNum == 1 && (teamsList1.contains(team1) || teamsList2.contains(team1))) {

            throw new FieldsValidator("Team 1 is already assigned to a match", "team1");
        }
        if (teamNum == 2 && (teamsList1.contains(team2) || teamsList2.contains(team2))) {
            throw new FieldsValidator("Team 2 is already assigned to a match", "team2");
        }

    }

}
