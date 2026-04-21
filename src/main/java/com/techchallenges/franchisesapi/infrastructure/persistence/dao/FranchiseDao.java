package com.techchallenges.franchisesapi.infrastructure.persistence.dao;

import com.techchallenges.franchisesapi.infrastructure.persistence.entity.FranchiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseDao extends JpaRepository<FranchiseEntity, Long> {

    FranchiseEntity findByName(String name);
}
