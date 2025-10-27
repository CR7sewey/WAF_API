package com.mike.waf.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class NotFoundFieldsValidator extends RuntimeException {

    private String field;

    public NotFoundFieldsValidator(String message, String field) {
        super(message);
        this.field = field;
    }

}
