package com.paulanussbrand.store.dto;

public record LoginRequest(
        String email,
        String password
) {
}
