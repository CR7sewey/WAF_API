package com.mike.waf.repository;

import com.mike.waf.model.entities.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PitchRepository extends JpaRepository<Pitch, UUID> {
}
