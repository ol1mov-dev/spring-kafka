package com.projects.core.product;

import lombok.Builder;

@Builder
public record ProductCreatedRes (
        String name,
        double price
){}
