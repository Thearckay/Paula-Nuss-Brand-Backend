package com.paulanussbrand.store.dto;

import java.util.UUID;

public record SearchPublicResultResponse(
        String sku,
        String name,
        String shortDescription,
        String imageUrl
) {
}
