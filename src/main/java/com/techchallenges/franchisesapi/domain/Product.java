package com.techchallenges.franchisesapi.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private final String name;
    private int stock;
}
