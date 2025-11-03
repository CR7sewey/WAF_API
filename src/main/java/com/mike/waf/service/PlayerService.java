package com.mike.waf.service;

import com.mike.waf.model.entities.Player;
import com.mike.waf.model.entities.Team;
import com.mike.waf.repository.PlayerRepository;
import com.mike.waf.repository.TeamRepository;
import com.mike.waf.security.CustomAuthentication;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.mike.waf.model.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService implements IService<Player> {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final UserService userService;

    @Override
    public Optional<Player> findById(UUID id) {
        var p = playerRepository.findById(id);

        return p;
    }

    @Override
    public Player save(Player player) {
        var user = getCurrentUser();
        player.setUser(user);
        return playerRepository.save(player);
    }

    @Override
    public void update(Player player) {
        if (player.getId() == null) {
            throw new IllegalArgumentException("Player not found");
        }
        playerRepository.save(player);
    }

    @Override
    public void delete(Player player) {
        removePlayerFromTeams(player);
        removePlayerFromUser();
        playerRepository.delete(player);
    }

    //@Override
    public Page<Player> findAllByNameAndRating(
            String name,
            Integer rating,
            Integer pageNumber,
            Integer pageSize

    ) {
        Specification<Player> sp = Specification.allOf(
                ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction())
        ); // create specification

        if (rating != null) {
            sp = sp.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rating"), rating)
            );
        }

        if (name != null) {
            sp = sp.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" +name + "%")
            );
        }


        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("rating").descending());
        return playerRepository.findAll(sp, pageable);
    }


    private void removePlayerFromTeams(Player player) {
        for (Team team : player.getTeams()) {
            team.getPlayers().remove(player);
        }
        teamRepository.saveAll(player.getTeams());
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuthentication) {
            var user = customAuthentication.getUser();
            return userService
                    .findByUsername(user.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found..."));
        }
        throw new IllegalArgumentException("Current user is not found");
    }

    private void removePlayerFromUser() {
        var user = getCurrentUser();
        if (user != null) {
            user.setPlayer(null);
            //userService.save(user);
        }
    }

}
