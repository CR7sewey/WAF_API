package com.mike.waf.model.DTO;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        @Size(min = 1, max = 30)
        @NotBlank
        String username,
        @Size(min = 1, max = 30)
        @NotBlank
        String password,
        @Email
        @NotBlank
        @Size(min = 1, max = 1024)
        String email,
        String phone,
        @Size(max = 1024)
        String location,
        List<String> roles

) {
}
