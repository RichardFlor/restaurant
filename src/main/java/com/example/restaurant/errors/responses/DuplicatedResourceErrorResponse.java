package com.example.restaurant.errors.responses;


import com.example.restaurant.errors.ErrorResponse;
import com.example.restaurant.errors.ExceptionCode;
import com.example.restaurant.errors.details.DuplicatedResourceErrorDetails;

public class DuplicatedResourceErrorResponse extends ErrorResponse<DuplicatedResourceErrorDetails> {
    public DuplicatedResourceErrorResponse(DuplicatedResourceErrorDetails details) {
        super(ExceptionCode.DUPLICATED_RESOURCE, details);
    }
}
