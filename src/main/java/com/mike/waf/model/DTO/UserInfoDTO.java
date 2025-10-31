package com.mike.waf.model.DTO;

public record UserInfoDTO(
        String username,
        String email,
        String phone,
        String location
) {
}
