package com.search.blog.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    NOT_EXISTS(400, "Not found Resources"),
    METHOD_NOT_ALLOWED(405, "This HttpMethod is not supported"),
    INTERNAL_SERVER_ERROR(500, "internal Server Error"),
    BAD_REQUEST(400, "parameter is not available");

    private int status;
    private String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
