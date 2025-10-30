package com.mike.waf.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FieldsValidator extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String field;

    public FieldsValidator(String message, String field) {
        super(message);
        this.field = field;
    }

}
