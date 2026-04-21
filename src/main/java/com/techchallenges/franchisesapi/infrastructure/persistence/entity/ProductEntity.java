package com.techchallenges.franchisesapi.infrastructure.persistence.entity;

import com.techchallenges.franchisesapi.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private int stock;

    public static ProductEntity fromDomain(Product product) {
        var productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setStock(product.getStock());
        return productEntity;
    }

    public Product toDomain() {
        return new Product(this.id, this.name, this.stock);
    }
}
