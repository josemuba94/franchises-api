package com.techchallenges.franchisesapi.infrastructure.persistence.dao;

import com.techchallenges.franchisesapi.infrastructure.persistence.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchDao extends JpaRepository<BranchEntity, Long> {

    BranchEntity findByName(String name);

    List<BranchEntity> findByFranchiseName(String franchiseName);
}
