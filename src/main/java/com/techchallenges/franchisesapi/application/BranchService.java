package com.techchallenges.franchisesapi.application;

import com.techchallenges.franchisesapi.domain.Branch;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface BranchService {

    Branch createBranch(String franchiseName, String branchName);

    Branch createProduct(String branchName, String productName, int stock);

    Branch updateProductStock(String branchName, String productName, int newStock);

    Branch deleteProduct(String branchName, String productName);

    @Cacheable("products-stock")
    List<Branch> getTopStockBranchesByFranchise(String franchiseName);

}
