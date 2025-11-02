package com.mike.waf.model.DTO;

import java.util.List;

public record UserInfoDTO(
        String username,
        String email,
        String phone,
        String location,
        List<String> roles
) {
}
