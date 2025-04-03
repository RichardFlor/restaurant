package com.example.restaurant.errors.details;

import java.util.Map;

public record EntityNotFoundErrorDetails(
        String entityClass,
        Map<String, Object> parameters
) {
}
