package com.mike.waf.model.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TeamDTO(
        UUID id,
        @Size(min = 1, max = 30)
        String name,
        @NotNull
        @Min(1)
        @Max(11)
        Integer size
) {
}
