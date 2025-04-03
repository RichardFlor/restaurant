package com.example.restaurant.errors.details;

public record HandleMethodArgumentNotValidErrorDetails(
        String field,
        String[] messages
) {
}
