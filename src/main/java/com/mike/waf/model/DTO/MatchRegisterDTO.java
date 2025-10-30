package com.mike.waf.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mike.waf.model.entities.Team;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record MatchRegisterDTO(
        UUID id,
        String result,
        @Future(message = "Date needs to be in the future")
        Instant date,
        UUID team1,
        UUID team2,
        @NotNull
        Boolean status,
        @NotNull(message = "Pitch id cannot be null")
        UUID pitchId
)
{
}
