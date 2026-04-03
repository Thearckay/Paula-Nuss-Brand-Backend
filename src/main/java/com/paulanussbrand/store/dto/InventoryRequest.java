package com.paulanussbrand.store.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public record InventoryRequest(
        String size,
        Integer quantity
) {
}
