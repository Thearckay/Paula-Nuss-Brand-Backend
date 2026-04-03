package com.paulanussbrand.store.dto;

import com.paulanussbrand.store.enums.ProductCollection;

import java.util.List;

public record FeaturedProductResponse(
        String sku,
        String imageUrl,
        String title,
        String description,
        List<String> colors,
        Integer stock,
        ProductCollection collection
) {
}
