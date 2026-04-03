package com.paulanussbrand.store.dto;

import com.paulanussbrand.store.enums.ProductCollection;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        String sku,
        String name,
        String shortDescription,
        String fullDescription,
        BigDecimal price,
        List<String> images,
        List<String> colors,
        List<InventoryRequest> inventory,
        ProductCollection collection,
        Boolean isFeatured
) {
}
