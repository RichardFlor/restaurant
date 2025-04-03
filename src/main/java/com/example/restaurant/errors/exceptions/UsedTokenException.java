package com.example.restaurant.errors.exceptions;

import com.example.restaurant.errors.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class UsedTokenException extends RuntimeException {
    private final ExceptionCode code;

    public UsedTokenException() {
        super(ExceptionCode.USED_TOKEN.toString());
        this.code = ExceptionCode.USED_TOKEN;
    }
    public String getCodeAsString() {
        return code.toString();
    }

}
