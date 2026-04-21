package com.techchallenges.franchisesapi.infrastructure.persistence.entity;

import com.techchallenges.franchisesapi.domain.Franchise;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "franchise")
@Getter
@Setter
@NoArgsConstructor
public class FranchiseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public static FranchiseEntity fromDomain(Franchise franchise) {
        var franchiseEntity = new FranchiseEntity();
        franchiseEntity.setId(franchise.getId());
        franchiseEntity.setName(franchise.getName());
        return franchiseEntity;
    }

    public Franchise toDomain() {
        return new Franchise(this.id, this.name);
    }
}
