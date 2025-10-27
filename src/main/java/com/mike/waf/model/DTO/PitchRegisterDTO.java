package com.mike.waf.model.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PitchRegisterDTO(
        UUID id,
        @NotBlank(message = "Name cannot be null")
        @Size(max = 255, min = 1)
        String name,
        @NotBlank(message = "Location cannot be null")
        @Size(max = 1024, min = 1)
        String location,
        String photoUrl,
        @NotBlank
        @Size(max = 24, min = 1)
        String type,
        Double length,
        Double width,
        @NotBlank()
        @Size(max = 16, min = 1)
        String pitchDimensions
) {
}
