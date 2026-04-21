package com.techchallenges.franchisesapi.domain.exception;

public class BranchNotFoundException extends RuntimeException {

    public BranchNotFoundException(String name) {
        super("Branch '" + name + "' not found.");
    }
}
