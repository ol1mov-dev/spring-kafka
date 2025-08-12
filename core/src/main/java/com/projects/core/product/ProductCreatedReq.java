package com.projects.core.product;

import lombok.Builder;

@Builder
public record ProductCreatedReq(
        String name,
        double price
){}
