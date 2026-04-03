package com.paulanussbrand.store.dto;

import com.paulanussbrand.store.enums.ProductCollection;

import java.math.BigDecimal;
import java.util.List;

// todo - aplicar o starter valid para validar as informações
public record ProductRequest(
        Boolean featured,
        String name,
        String sku,
        BigDecimal price,
        String shortDescription,
        List<String> colors,
        ProductCollection collection,
        List<InventoryRequest> inventory,
        String fullDescription
) {
}
