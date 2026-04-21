package com.techchallenges.franchisesapi.domain.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String name) {
        super("Product '" + name + "' was not found.");
    }
}
