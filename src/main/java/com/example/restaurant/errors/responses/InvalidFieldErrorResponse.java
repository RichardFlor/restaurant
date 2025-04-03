package com.example.restaurant.errors.responses;


import com.example.restaurant.errors.ErrorResponse;
import com.example.restaurant.errors.ExceptionCode;
import com.example.restaurant.errors.details.InvalidFieldErrorDetails;

public class InvalidFieldErrorResponse extends ErrorResponse<InvalidFieldErrorDetails> {
    public InvalidFieldErrorResponse(InvalidFieldErrorDetails details) {
        super(ExceptionCode.API_FIELDS_INVALID, details);
    }
}
