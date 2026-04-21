package com.techchallenges.franchisesapi.infrastructure.controllers;

import com.techchallenges.franchisesapi.application.BranchService;
import com.techchallenges.franchisesapi.infrastructure.controllers.request.CreateBranchRequest;
import com.techchallenges.franchisesapi.infrastructure.controllers.request.CreateOrUpdateProductRequest;
import com.techchallenges.franchisesapi.infrastructure.controllers.request.DeleteProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch")
public class BranchController {

    public static final String PRODUCT_MAPPING = "/product";
    public static final String TOP_STOCK_URL = "/top-stock/{franchiseName}";

    @Autowired
    private BranchService branchService;

    @PostMapping
    public ResponseEntity<Object> createBranch(@RequestBody CreateBranchRequest request) {
        var branch = branchService.createBranch(request.franchiseName(), request.branchName());
        return ResponseEntity.ok(branch);
    }

    @PostMapping(PRODUCT_MAPPING)
    public ResponseEntity<Object> createProduct(@RequestBody CreateOrUpdateProductRequest request) {
        var branch = branchService.createProduct(request.branchName(), request.productName(), request.stock());
        return ResponseEntity.ok(branch);
    }

    @PatchMapping(PRODUCT_MAPPING)
    public ResponseEntity<Object> updateProductBranch(@RequestBody CreateOrUpdateProductRequest request) {
        var branch = branchService.updateProductStock(request.branchName(), request.productName(), request.stock());
        return ResponseEntity.ok(branch);
    }

    @DeleteMapping(PRODUCT_MAPPING)
    public ResponseEntity<Object> deleteProduct(@RequestBody DeleteProductRequest request) {
        var branch = branchService.deleteProduct(request.branchName(), request.productName());
        return ResponseEntity.ok(branch);
    }

    @GetMapping(TOP_STOCK_URL)
    public ResponseEntity<Object> getTopStockBranchesByFranchise(@PathVariable String franchiseName) {
        var branches = branchService.getTopStockBranchesByFranchise(franchiseName);
        return ResponseEntity.ok(branches);
    }

}
