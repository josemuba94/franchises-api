package com.techchallenges.franchisesapi.infrastructure.persistence;

import com.techchallenges.franchisesapi.domain.Branch;
import com.techchallenges.franchisesapi.domain.repository.BranchRepository;
import com.techchallenges.franchisesapi.infrastructure.persistence.dao.BranchDao;
import com.techchallenges.franchisesapi.infrastructure.persistence.entity.BranchEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class BranchRepositoryImpl implements BranchRepository {

    @Autowired
    private BranchDao branchDao;

    @Override
    public Branch createBranch(Branch branch) {
        var branchEntity = BranchEntity.fromDomain(branch);
        branchEntity = branchDao.save(branchEntity);
        log.info("Branch named {} created for franchise {}!", branchEntity.getName(), branchEntity.getFranchise().getName());
        return branchEntity.toDomain();
    }

    @Override
    public Branch updateBranch(Branch branch) {
        var branchEntity = BranchEntity.fromDomain(branch);
        branchEntity = branchDao.save(branchEntity);
        log.info("Branch named {} updated!", branchEntity.getName());
        return branchEntity.toDomain();
    }

    @Override
    public Optional<Branch> findByName(String name) {
        var branchEntity = branchDao.findByName(name);
        log.info("Branch named {} {} found!", name, branchEntity != null ? "was" : "was not");
        return Optional.ofNullable(branchEntity).map(BranchEntity::toDomain);
    }

    @Override
    public List<Branch> findByFranchiseName(String franchiseName) {
        log.info("Fetching all branches for franchise named {}", franchiseName);
        return branchDao.findByFranchiseName(franchiseName).stream()
                .map(BranchEntity::toDomain)
                .toList();
    }
}
