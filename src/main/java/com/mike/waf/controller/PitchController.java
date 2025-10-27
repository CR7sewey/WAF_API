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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pitches")
@RequiredArgsConstructor
public class PitchController {

    private final PitchService pitchService;

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

}
