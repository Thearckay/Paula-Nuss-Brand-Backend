package com.paulanussbrand.store.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCatalogAdmResponse(
        UUID id,
        String name,
        String shortDescription,
        String imageUrl,
        BigDecimal price
) {
}
