package com.mike.waf.model.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PlayerStatisticsDTO(
        @Min(0)
        Integer gamesPlayed,
        @Min(0)
        Integer goals,
        @Min(0)
        Integer assists,
        @Min(0)
        @Max(100)
        Integer rating

) {
}
