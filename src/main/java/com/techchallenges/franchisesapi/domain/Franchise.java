package com.techchallenges.franchisesapi.domain;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Franchise {
    private Long id;
    private final String name;
}
