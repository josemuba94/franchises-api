package com.techchallenges.franchisesapi.domain;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Branch {
    private Long id;
    private final String name;
    private final Franchise franchise;
    private List<Product> products = new LinkedList<>();
}
