package com.mike.waf.controller;

import com.mike.waf.model.DTO.ResponseErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getFieldErrors();
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Parameters invalid",
                getErrors(fieldErrors)
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseErrorDTO);

    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleOperationNotAllowed(RuntimeException e, HttpServletRequest request){
        ResponseErrorDTO errorDTO = new ResponseErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of()
        );
        return ResponseEntity.status(errorDTO.status()).body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleRunTimeException(RuntimeException e, HttpServletRequest request){
        ResponseErrorDTO errorDTO = new ResponseErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                List.of()
        );
        return ResponseEntity.status(errorDTO.status()).body(errorDTO);
    }


    // utils
    private List<Map<String,String>> getErrors(List<FieldError> fieldError){
        return fieldError.stream().map(value -> {
            HashMap<String,String> map = new HashMap<>();
            map.put("field", value.getField());
            map.put("message", value.getDefaultMessage());
            return map;

        }).collect(Collectors.toList());
    }

}
