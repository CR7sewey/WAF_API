package com.mike.waf.controller;

import com.mike.waf.model.DTO.PitchRegisterDTO;
import com.mike.waf.model.DTO.PlayerRegisterDTO;
import com.mike.waf.model.entities.Pitch;
import com.mike.waf.model.entities.Player;
import com.mike.waf.repository.PitchRepository;
import com.mike.waf.service.PitchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pitches")
@RequiredArgsConstructor
public class PitchController {

    private final PitchService pitchService;

    @PreAuthorize("hasAnyRole('Admin','ADMIN')")
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PitchRegisterDTO pitchRegisterDTO) {
        Pitch pitch = new Pitch();
        pitch.setName(pitchRegisterDTO.name());
        pitch.setLocation(pitchRegisterDTO.location());
        pitch.setPhotoUrl(pitchRegisterDTO.photoUrl());
        pitch.setType(pitchRegisterDTO.type());
        pitch.setLength(pitchRegisterDTO.length());
        pitch.setWidth(pitchRegisterDTO.width());
        pitch.setPitchDimensions(pitchRegisterDTO.pitchDimensions());
        var pt =  pitchService.save(pitch);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(pt.getId());
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pitch> findById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return pitchService.findById(uuid)
                .map(pitch -> ResponseEntity.ok().body(pitch))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Page<Pitch>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String pitchDimensions,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {

        Page<Pitch> page = pitchService.findAll(name, location, pitchDimensions, pageNumber, pageSize);

        return ResponseEntity.ok().body(page);

    }
    @PreAuthorize("hasAnyRole('Admin','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid PitchRegisterDTO pitchRegisterDTO) {
        UUID uuid = UUID.fromString(id);
        Optional<Pitch> p = pitchService.findById(uuid);
        if (p.isPresent()) {
            var pitch = p.get();
            pitch.setName(pitchRegisterDTO.name());
            pitch.setLocation(pitchRegisterDTO.location());
            pitch.setPhotoUrl(pitchRegisterDTO.photoUrl());
            pitch.setType(pitchRegisterDTO.type());
            pitch.setLength(pitchRegisterDTO.length());
            pitch.setWidth(pitchRegisterDTO.width());
            pitch.setPitchDimensions(pitchRegisterDTO.pitchDimensions());
            pitchService.update(pitch);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }
    @PreAuthorize("hasAnyRole('Admin','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return pitchService.findById(uuid).map(p -> {
            pitchService.delete(p);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
