package com.techchallenges.franchisesapi.infrastructure.controllers;

import com.techchallenges.franchisesapi.application.FranchiseService;
import com.techchallenges.franchisesapi.infrastructure.controllers.request.CreateFranchiseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/franchise")
public class FranchiseController {

    @Autowired
    private FranchiseService franchiseService;

    @PostMapping
    public ResponseEntity<Object> createFranchise(@RequestBody CreateFranchiseRequest request) {
        var franchise = franchiseService.createFranchise(request.name());
        return ResponseEntity.ok(franchise);
    }
}
