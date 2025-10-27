package com.mike.waf.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mike.waf.model.entities.Team;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record MatchRegisterDTO(
        UUID id,
        String result,
        Instant date,
        UUID team1,
        UUID team2,
        @NotNull
        Boolean status
)
{
}
