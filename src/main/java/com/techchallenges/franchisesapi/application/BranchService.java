package com.techchallenges.franchisesapi.application;

import com.techchallenges.franchisesapi.domain.Branch;

import java.util.List;

public interface BranchService {

    Branch createBranch(String franchiseName, String branchName);

    Branch createProduct(String branchName, String productName, int stock);

    Branch updateProductStock(String branchName, String productName, int newStock);

    Branch deleteProduct(String branchName, String productName);

    List<Branch> getTopStockBranchesByFranchise(String franchiseName);

}
