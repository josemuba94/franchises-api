package com.techchallenges.franchisesapi.domain.exception;

public class FranchiseNotFoundException extends RuntimeException {

    public FranchiseNotFoundException(String name) {
        super("Franchise '" + name + "' not found.");
    }
}
