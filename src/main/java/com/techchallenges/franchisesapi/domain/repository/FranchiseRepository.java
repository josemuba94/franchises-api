package com.techchallenges.franchisesapi.domain.repository;

import com.techchallenges.franchisesapi.domain.Franchise;

import java.util.Optional;

public interface FranchiseRepository {

    Franchise createFranchise(Franchise franchise);

    Optional<Franchise> findByName(String name);
}
