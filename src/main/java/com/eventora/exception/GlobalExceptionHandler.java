package com.eventora.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();

        List<String> messeges =  result.getFieldErrors()
                .stream().map(err->err.getField()+" : "+err.getDefaultMessage()).toList();

        return ResponseEntity.badRequest().body(messeges);
    }
}
