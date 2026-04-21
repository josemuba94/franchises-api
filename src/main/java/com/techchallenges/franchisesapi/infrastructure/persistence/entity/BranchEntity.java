package com.techchallenges.franchisesapi.infrastructure.persistence.entity;

import com.techchallenges.franchisesapi.domain.Branch;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "branch")
@Getter
@Setter
@NoArgsConstructor
public class BranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "franchise_id", nullable = false)
    private FranchiseEntity franchise;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = false)
    private List<ProductEntity> products;

    public static BranchEntity fromDomain(Branch branch) {
        var products = branch.getProducts().stream()
                .map(ProductEntity::fromDomain)
                .toList();

        var branchEntity = new BranchEntity();
        branchEntity.setId(branch.getId());
        branchEntity.setName(branch.getName());
        branchEntity.setFranchise(FranchiseEntity.fromDomain(branch.getFranchise()));
        branchEntity.setProducts(products);

        return branchEntity;
    }

    public Branch toDomain() {
        var products = new ArrayList<>(this.products.stream()
                .map(ProductEntity::toDomain)
                .toList());
        return new Branch(this.id, this.name, this.franchise.toDomain(), products);
    }
}
