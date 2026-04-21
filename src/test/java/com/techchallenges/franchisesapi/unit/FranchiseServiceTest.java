package com.techchallenges.franchisesapi.unit;

import com.techchallenges.franchisesapi.application.FranchiseService;
import com.techchallenges.franchisesapi.application.serviceimpl.FranchiseServiceImpl;
import com.techchallenges.franchisesapi.domain.Franchise;
import com.techchallenges.franchisesapi.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class FranchiseServiceTest {

    private static final String FRANCHISE_NAME = "fake-franchise";

    @Mock
    private FranchiseRepository franchiseRepository;

    private FranchiseService franchiseService;

    @Captor
    private ArgumentCaptor<Franchise> franchiseCaptor;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        franchiseService = new FranchiseServiceImpl(franchiseRepository);
    }

    @Test
    void createFranchiseTest() {
        franchiseService.createFranchise(FRANCHISE_NAME);
        Mockito.verify(franchiseRepository, times(1)).createFranchise(franchiseCaptor.capture());
        Assertions.assertEquals(FRANCHISE_NAME, franchiseCaptor.getValue().getName());
    }

    @Test
    void createFranchise_alreadyExists_returnsExistingFranchise() {
        var existing = new Franchise(FRANCHISE_NAME);
        Mockito.when(franchiseRepository.findByName(FRANCHISE_NAME)).thenReturn(Optional.of(existing));

        var result = franchiseService.createFranchise(FRANCHISE_NAME);

        Mockito.verify(franchiseRepository, times(0)).createFranchise(any());
        Assertions.assertEquals(existing.getName(), result.getName());
    }
}
