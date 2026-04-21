package com.techchallenges.franchisesapi.application.serviceimpl;

import com.techchallenges.franchisesapi.application.BranchService;
import com.techchallenges.franchisesapi.domain.Branch;
import com.techchallenges.franchisesapi.domain.Product;
import com.techchallenges.franchisesapi.domain.exception.BranchNotFoundException;
import com.techchallenges.franchisesapi.domain.exception.FranchiseNotFoundException;
import com.techchallenges.franchisesapi.domain.exception.ProductNotFoundException;
import com.techchallenges.franchisesapi.domain.repository.BranchRepository;
import com.techchallenges.franchisesapi.domain.repository.FranchiseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public BranchServiceImpl(FranchiseRepository franchiseRepository, BranchRepository branchRepository) {
        this.franchiseRepository = franchiseRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch createBranch(String franchiseName, String branchName) {
        var franchise = franchiseRepository.findByName(franchiseName)
                .orElseThrow(() -> new FranchiseNotFoundException(franchiseName));

        var branchOpt = branchRepository.findByName(branchName);
        if (branchOpt.isPresent()) {
            log.info("Branch {} already exists!", branchName);
            return branchOpt.get();
        }

        log.info("Attempting to create a new branch named {} for franchise named {}", branchName, franchiseName);
        var branch = new Branch(branchName, franchise);
        return branchRepository.createBranch(branch);
    }
    @Override
    public Branch createProduct(String branchName, String productName, int stock) {
        var branch = branchRepository.findByName(branchName)
                .orElseThrow(() -> new BranchNotFoundException(branchName));

        var existentProduct = branch.getProducts().stream()
                .anyMatch(product -> product.getName().equals(productName));
        if (existentProduct) {
            log.info("Product {} already exists!", productName);
            return branch;
        }

        var product = new Product(productName);
        product.setStock(stock);

        log.info("Attempting to create product named {} for branch {}", productName, branchName);
        branch.getProducts().add(product);
        return branchRepository.updateBranch(branch);
    }

    @Override
    public Branch deleteProduct(String branchName, String productName) {
        log.info("Attempting to delete product {} from branch {}", productName, branchName);
        var branch = branchRepository.findByName(branchName)
                .orElseThrow(() -> new BranchNotFoundException(branchName));

        var removed = branch.getProducts().removeIf(p -> p.getName().equals(productName));
        if (!removed)
            throw new ProductNotFoundException(productName);

        return branchRepository.updateBranch(branch);
    }

    @Override
    public List<Branch> getTopStockBranchesByFranchise(String franchiseName) {
        franchiseRepository.findByName(franchiseName)
                .orElseThrow(() -> new FranchiseNotFoundException(franchiseName));

        log.info("Fetching top-stock products per branch for franchise {}", franchiseName);
        var branches = branchRepository.findByFranchiseName(franchiseName);

        branches.forEach(branch -> {
            int maxStock = branch.getProducts().stream()
                    .mapToInt(Product::getStock)
                    .max()
                    .orElse(0);

            branch.setProducts(branch.getProducts().stream()
                    .filter(p -> p.getStock() == maxStock)
                    .toList());
        });
        return branches;
    }

    @Override
    public Branch updateProductStock(String branchName, String productName, int newStock) {
        log.info("Attempting to update the product {} in the branch {}, stock to '{}'", productName, branchName, newStock);
        var branch = branchRepository.findByName(branchName)
                .orElseThrow(() -> new BranchNotFoundException(branchName));

        var productOpt = branch.getProducts().stream()
            .filter(prod -> prod.getName().equals(productName))
            .findFirst();

        if (productOpt.isPresent())
            productOpt.get().setStock(newStock);
        else
            throw new ProductNotFoundException(productName);

        branchRepository.updateBranch(branch);
        return branch;
    }
}
