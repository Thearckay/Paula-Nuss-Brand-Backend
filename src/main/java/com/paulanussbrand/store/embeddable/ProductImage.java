package com.paulanussbrand.store.embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductImage(
        String url,
        String publicId
) {
}
