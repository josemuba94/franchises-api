package com.techchallenges.franchisesapi.infrastructure.persistence;

import com.techchallenges.franchisesapi.domain.Franchise;
import com.techchallenges.franchisesapi.domain.repository.FranchiseRepository;
import com.techchallenges.franchisesapi.infrastructure.persistence.dao.FranchiseDao;
import com.techchallenges.franchisesapi.infrastructure.persistence.entity.FranchiseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class FranchiseRepositoryImpl implements FranchiseRepository {

    @Autowired
    private FranchiseDao franchiseDao;

    @Override
    public Franchise createFranchise(Franchise franchise) {
        var franchiseEntity = FranchiseEntity.fromDomain(franchise);
        franchiseEntity = franchiseDao.save(franchiseEntity);
        log.info("Franchise named {} created!", franchiseEntity.getName());
        return franchiseEntity.toDomain();
    }

    @Override
    public Optional<Franchise> findByName(String name) {
        var franchiseEntity = franchiseDao.findByName(name);
        log.info("Franchise named {} {} found!", name, franchiseEntity != null ? "was" : "was not");
        return Optional.ofNullable(franchiseEntity).map(FranchiseEntity::toDomain);
    }
}
