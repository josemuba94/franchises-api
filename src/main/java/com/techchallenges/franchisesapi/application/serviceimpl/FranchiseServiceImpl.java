package com.techchallenges.franchisesapi.application.serviceimpl;

import com.techchallenges.franchisesapi.application.FranchiseService;
import com.techchallenges.franchisesapi.domain.Franchise;
import com.techchallenges.franchisesapi.domain.repository.FranchiseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public FranchiseServiceImpl(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Franchise createFranchise(String franchiseName) {
        var franchiseOpt = franchiseRepository.findByName(franchiseName);
        if (franchiseOpt.isPresent()) {
            log.info("Franchise {} already exists!", franchiseName);
            return franchiseOpt.get();
        }
        log.info("Attempting to create a new franchise named {}", franchiseName);
        return franchiseRepository.createFranchise(new Franchise(franchiseName));
    }
}
