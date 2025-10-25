package com.mike.waf.service;

import com.mike.waf.model.entities.Team;
import com.mike.waf.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
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
public class TeamService implements IService<Team> {

    private final TeamRepository teamRepository;

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Optional<Team> findById(UUID id) {
        return teamRepository.findById(id);
    }


    @Override
    public void update(Team team) {
        if (team.getId() == null) {
            throw new IllegalArgumentException("Team not found");
        }
        teamRepository.save(team);

    }

    @Override
    public void delete(Team team) {
        teamRepository.delete(team);
    }

    public Page<Team> findAllByNameAndRating(
            String name,
            Integer rating,
            Integer pageNumber,
            Integer pageSize

    ) {
        Specification<Team> sp = Specification.allOf(
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
        return teamRepository.findAll(sp, pageable);
    }
}
