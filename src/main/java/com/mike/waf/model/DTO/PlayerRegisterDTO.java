package com.mike.waf.model.DTO;

import com.mike.waf.model.enums.Positions;
import jakarta.validation.constraints.*;

import java.util.List;

public record PlayerRegisterDTO(
        @NotBlank(message = "Name is mandatory.")
        @Size(min = 1, max = 20)
        String name,
        @NotNull(message = "Age is mandatory")
        @Min(16)
        @Max(120)
        Integer age,
        Positions favoritePosition,
        String favoriteTeam,
        String favoritePlayer,
        List<String> skill
) {
}
