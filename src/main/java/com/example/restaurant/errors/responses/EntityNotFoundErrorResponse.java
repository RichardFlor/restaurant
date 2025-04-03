package com.example.restaurant.errors.responses;


import com.example.restaurant.errors.ErrorResponse;
import com.example.restaurant.errors.ExceptionCode;
import com.example.restaurant.errors.details.EntityNotFoundErrorDetails;

public class EntityNotFoundErrorResponse extends ErrorResponse<EntityNotFoundErrorDetails> {
    public EntityNotFoundErrorResponse(EntityNotFoundErrorDetails details) {
        super(ExceptionCode.ENTITY_NOT_FOUND, details);
    }
}
