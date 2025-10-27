package com.mike.waf.service;

import com.mike.waf.model.entities.Pitch;
import com.mike.waf.repository.PitchRepository;
import lombok.RequiredArgsConstructor;
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

    }

    @Override
    public void delete(Pitch pitch) {

    }
}
