package com.mike.waf.exceptions;

import lombok.Getter;

@Getter
public class AuthorizationValidator extends RuntimeException {

    public AuthorizationValidator(String message) {

        super(message);
    }
}
