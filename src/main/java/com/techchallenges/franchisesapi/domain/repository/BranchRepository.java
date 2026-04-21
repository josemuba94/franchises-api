package com.techchallenges.franchisesapi.domain.repository;

import com.techchallenges.franchisesapi.domain.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepository {

    Branch createBranch(Branch branch);

    Branch updateBranch(Branch branch);

    Optional<Branch> findByName(String name);

    List<Branch> findByFranchiseName(String franchiseName);
}
