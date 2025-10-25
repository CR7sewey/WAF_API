package com.mike.waf.model.DTO;

import java.util.List;
import java.util.Map;

public record ResponseErrorDTO(
        Integer status,
        String message,
        List<Map<String, String>> fieldErrors
) {
}
