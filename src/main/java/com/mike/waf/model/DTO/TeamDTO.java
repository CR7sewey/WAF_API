package com.mike.waf.model.DTO;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TeamDTO(
        UUID id,
        @Size(min = 1, max = 30)
        String name
) {
}
