package com.search.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private HttpStatus httpStatus;
    private String message;

    public BusinessException(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
