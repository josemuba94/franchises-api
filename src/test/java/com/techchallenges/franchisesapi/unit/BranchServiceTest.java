package com.techchallenges.franchisesapi.unit;

import com.techchallenges.franchisesapi.application.BranchService;
import com.techchallenges.franchisesapi.application.serviceimpl.BranchServiceImpl;
import com.techchallenges.franchisesapi.domain.Branch;
import com.techchallenges.franchisesapi.domain.Franchise;
import com.techchallenges.franchisesapi.domain.Product;
import com.techchallenges.franchisesapi.domain.exception.BranchNotFoundException;
import com.techchallenges.franchisesapi.domain.exception.FranchiseNotFoundException;
import com.techchallenges.franchisesapi.domain.exception.ProductNotFoundException;
import com.techchallenges.franchisesapi.domain.repository.BranchRepository;
import com.techchallenges.franchisesapi.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class BranchServiceTest {

    private static final String FRANCHISE_NAME = "KFC";
    private static final String BRANCH_NAME = "KFC-N";
    private static final String BRANCH_NAME_SOUTH = "KFC-S";
    private static final String PRODUCT_WINGS = "Wings";
    private static final String PRODUCT_POPCORN = "Popcorn";
    private static final String PRODUCT_STRIPES = "Stripes";
    private static final String PRODUCT_BURGER = "Burger";
    private static final String PRODUCT_COMBO = "Combo1";

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchRepository branchRepository;

    private BranchService branchService;

    @Captor
    private ArgumentCaptor<Branch> branchCaptor;

    private Franchise franchise;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        branchService = new BranchServiceImpl(franchiseRepository, branchRepository);
        franchise = new Franchise(FRANCHISE_NAME);
    }

    @Test
    void createBranch_franchiseNotFound_throwsFranchiseNotFoundException() {
        Mockito.when(franchiseRepository.findByName(FRANCHISE_NAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(FranchiseNotFoundException.class,
                () -> branchService.createBranch(FRANCHISE_NAME, BRANCH_NAME));
    }

    @Test
    void createBranch_branchAlreadyExists_returnsExistingBranch() {
        var existing = new Branch(BRANCH_NAME, franchise);
        Mockito.when(franchiseRepository.findByName(FRANCHISE_NAME)).thenReturn(Optional.of(franchise));
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.of(existing));

        var result = branchService.createBranch(FRANCHISE_NAME, BRANCH_NAME);

        Mockito.verify(branchRepository, times(0)).createBranch(any());
        Assertions.assertEquals(existing.getName(), result.getName());
    }

    @Test
    void createBranch_newBranch_createsAndReturnsBranch() {
        Mockito.when(franchiseRepository.findByName(FRANCHISE_NAME)).thenReturn(Optional.of(franchise));
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.empty());

        branchService.createBranch(FRANCHISE_NAME, BRANCH_NAME);
        Mockito.verify(branchRepository, times(1)).createBranch(branchCaptor.capture());

        var createdBranch = branchCaptor.getValue();
        Assertions.assertEquals(BRANCH_NAME, createdBranch.getName());
        Assertions.assertEquals(FRANCHISE_NAME, createdBranch.getFranchise().getName());
    }

    @Test
    void createProduct_branchNotFound_throwsBranchNotFoundException() {
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(BranchNotFoundException.class,
                () -> branchService.createProduct(BRANCH_NAME, PRODUCT_WINGS, 10));
    }

    @Test
    void createProduct_productAlreadyExists_returnsUnchangedBranch() {
        var wings = new Product(PRODUCT_WINGS);
        wings.setStock(10);
        var branch = new Branch(1L, BRANCH_NAME, franchise, new ArrayList<>(List.of(wings)));
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.of(branch));

        var result = branchService.createProduct(BRANCH_NAME, PRODUCT_WINGS, 5);

        Mockito.verify(branchRepository, times(0)).updateBranch(any());
        Assertions.assertEquals(1, result.getProducts().size());
    }

    @Test
    void createProduct_newProduct_addsProductAndReturnsBranch() {
        var branch = new Branch(BRANCH_NAME, franchise);
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.of(branch));

        branchService.createProduct(BRANCH_NAME, PRODUCT_WINGS, 10);
        Mockito.verify(branchRepository, times(1)).updateBranch(branchCaptor.capture());

        var products = branchCaptor.getValue().getProducts();
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals(PRODUCT_WINGS, products.get(0).getName());
        Assertions.assertEquals(10, products.get(0).getStock());
    }

    @Test
    void deleteProduct_branchNotFound_throwsBranchNotFoundException() {
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(BranchNotFoundException.class,
                () -> branchService.deleteProduct(BRANCH_NAME, PRODUCT_WINGS));
    }

    @Test
    void deleteProduct_productNotFound_throwsProductNotFoundException() {
        var branch = new Branch(BRANCH_NAME, franchise);
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.of(branch));
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> branchService.deleteProduct(BRANCH_NAME, PRODUCT_WINGS));
    }

    @Test
    void deleteProduct_productFound_removesProductAndReturnsBranch() {
        var wings = new Product(PRODUCT_WINGS);
        var branch = new Branch(1L, BRANCH_NAME, franchise, new ArrayList<>(List.of(wings)));
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.of(branch));

        branchService.deleteProduct(BRANCH_NAME, PRODUCT_WINGS);
        Mockito.verify(branchRepository, times(1)).updateBranch(branchCaptor.capture());

        Assertions.assertTrue(branchCaptor.getValue().getProducts().isEmpty());
    }

    @Test
    void updateProductStock_branchNotFound_throwsBranchNotFoundException() {
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(BranchNotFoundException.class,
                () -> branchService.updateProductStock(BRANCH_NAME, PRODUCT_WINGS, 20));
    }

    @Test
    void updateProductStock_productNotFound_throwsProductNotFoundException() {
        var branch = new Branch(BRANCH_NAME, franchise);
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.of(branch));
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> branchService.updateProductStock(BRANCH_NAME, PRODUCT_WINGS, 20));
    }

    @Test
    void updateProductStock_productFound_updatesStockAndReturnsBranch() {
        var wings = new Product(PRODUCT_WINGS);
        wings.setStock(5);
        var branch = new Branch(1L, BRANCH_NAME, franchise, new ArrayList<>(List.of(wings)));
        Mockito.when(branchRepository.findByName(BRANCH_NAME)).thenReturn(Optional.of(branch));

        var result = branchService.updateProductStock(BRANCH_NAME, PRODUCT_WINGS, 20);
        Mockito.verify(branchRepository, times(1)).updateBranch(any());

        Assertions.assertEquals(20, result.getProducts().get(0).getStock());
    }

    @Test
    void getTopStockBranchesByFranchise_franchiseNotFound_throwsFranchiseNotFoundException() {
        Mockito.when(franchiseRepository.findByName(FRANCHISE_NAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(FranchiseNotFoundException.class,
                () -> branchService.getTopStockBranchesByFranchise(FRANCHISE_NAME));
    }

    @Test
    void getTopStockBranchesByFranchise_branchesWithProducts_returnsOnlyMaxStockProducts() {
        var wings = new Product(1L, PRODUCT_WINGS, 12);
        var popcorn = new Product(2L, PRODUCT_POPCORN, 9);
        var stripes = new Product(3L, PRODUCT_STRIPES, 12);
        var branchN = new Branch(1L, BRANCH_NAME, franchise, new ArrayList<>(List.of(wings, popcorn, stripes)));

        var burger = new Product(4L, PRODUCT_BURGER, 20);
        var combo = new Product(5L, PRODUCT_COMBO, 7);
        var branchS = new Branch(2L, BRANCH_NAME_SOUTH, franchise, new ArrayList<>(List.of(burger, combo)));

        Mockito.when(franchiseRepository.findByName(FRANCHISE_NAME)).thenReturn(Optional.of(franchise));
        Mockito.when(branchRepository.findByFranchiseName(FRANCHISE_NAME)).thenReturn(new ArrayList<>(List.of(branchN, branchS)));

        var result = branchService.getTopStockBranchesByFranchise(FRANCHISE_NAME);

        Assertions.assertEquals(2, result.size());
        var topN = result.get(0).getProducts();
        Assertions.assertEquals(2, topN.size());
        Assertions.assertTrue(topN.stream().noneMatch(p -> p.getName().equals(PRODUCT_POPCORN)));
        var topS = result.get(1).getProducts();
        Assertions.assertEquals(1, topS.size());
        Assertions.assertEquals(PRODUCT_BURGER, topS.get(0).getName());
    }

    @Test
    void getTopStockBranchesByFranchise_branchWithNoProducts_returnsEmptyProductsList() {
        var emptyBranch = new Branch(BRANCH_NAME, franchise);
        Mockito.when(franchiseRepository.findByName(FRANCHISE_NAME)).thenReturn(Optional.of(franchise));
        Mockito.when(branchRepository.findByFranchiseName(FRANCHISE_NAME)).thenReturn(new ArrayList<>(List.of(emptyBranch)));

        var result = branchService.getTopStockBranchesByFranchise(FRANCHISE_NAME);

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).getProducts().isEmpty());
    }
}
