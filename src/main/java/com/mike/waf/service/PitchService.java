package com.mike.waf.service;

import com.mike.waf.model.entities.Pitch;
import com.mike.waf.repository.PitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PitchService implements IService<Pitch> {

    private final PitchRepository pitchRepository;


    @Override
    public Optional<Pitch> findById(UUID id) {

        return pitchRepository.findById(id);
    }

    @Override
    public Pitch save(Pitch pitch) {
        return pitchRepository.save(pitch);
    }

    @Override
    public void update(Pitch pitch) {
        if (pitch.getId() == null) {
            throw new IllegalArgumentException("Pitch id must not be null");
        }
        pitchRepository.save(pitch);

    }

    @Override
    public void delete(Pitch pitch) {
        pitchRepository.delete(pitch);
    }

    public Page<Pitch> findAll(
            String name,
            String location,
            String pitchDimensions,
            Integer pageNumber,
            Integer pageSize
    ) {

        Specification<Pitch> specification = Specification.allOf(
                ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction())
        );

        if (name != null) {
            specification = specification.and(
                    ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + name + "%"))
            );
        }
        if (location != null) {
            specification = specification.and(
                    ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("location")), "%" + location + "%"))
            );
        }
        if (pitchDimensions != null) {
            specification = specification.and(
                    ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("pitchDimensions")), "%" + pitchDimensions + "%"))
            );
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return pitchRepository.findAll(specification, pageable);


    }

}
