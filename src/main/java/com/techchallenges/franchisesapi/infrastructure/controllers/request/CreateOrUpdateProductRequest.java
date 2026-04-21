package com.techchallenges.franchisesapi.infrastructure.controllers.request;

public record CreateOrUpdateProductRequest(String productName, String branchName, int stock) { }
