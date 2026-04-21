package com.techchallenges.franchisesapi.infrastructure.controllers;

import com.techchallenges.franchisesapi.domain.exception.BranchNotFoundException;
import com.techchallenges.franchisesapi.domain.exception.FranchiseNotFoundException;
import com.techchallenges.franchisesapi.domain.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({FranchiseNotFoundException.class, BranchNotFoundException.class, ProductNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
