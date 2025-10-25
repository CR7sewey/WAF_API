package com.mike.waf.service;

import com.mike.waf.model.entities.Player;
import com.mike.waf.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService implements IService<Player> {

    private final PlayerRepository playerRepository;

    @Override
    public Optional<Player> findById(UUID id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player save(Player player) {
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
        playerRepository.delete(player);
    }

    @Override
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

}
