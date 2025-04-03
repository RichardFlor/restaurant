package com.example.restaurant.errors.responses;


import com.example.restaurant.errors.ErrorResponse;
import com.example.restaurant.errors.ExceptionCode;
import com.example.restaurant.errors.details.InternalServerErrorDetails;

public class InternalServerErrorResponse extends ErrorResponse<InternalServerErrorDetails> {
    public InternalServerErrorResponse(InternalServerErrorDetails details) {
        super(ExceptionCode.INTERNAL_SERVER_ERROR, details);
    }
}
