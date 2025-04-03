package com.example.restaurant.errors.responses;


import com.example.restaurant.errors.ErrorResponse;
import com.example.restaurant.errors.ExceptionCode;
import com.example.restaurant.errors.details.HandleMethodArgumentNotValidErrorDetails;

public class HandleMethodArgumentNotValidErrorResponse extends ErrorResponse<HandleMethodArgumentNotValidErrorDetails> {
    public HandleMethodArgumentNotValidErrorResponse(HandleMethodArgumentNotValidErrorDetails details) {
        super(ExceptionCode.API_FIELDS_INVALID, details);
    }
}
