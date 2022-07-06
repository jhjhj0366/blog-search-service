package com.search.blog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private ErrorCode errorCode;
    private int status;
    private String message;
    private List<FieldError> errors;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldError {

        private String field;
        private String value;
        private String reason;

    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return ErrorResponse
                .builder()
                .errorCode(errorCode)
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(bindingResult.getFieldErrors()
                        .parallelStream()
                        .map(
                                error -> ErrorResponse.FieldError
                                        .builder()
                                        .reason(error.getDefaultMessage())
                                        .value(String.valueOf(error.getRejectedValue()))
                                        .field(error.getField())
                                        .build()
                        )
                        .collect(Collectors.toList()))
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        return ErrorResponse
                .builder()
                .errorCode(errorCode)
                .message(message)
                .status(httpStatus.value())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse
                .builder()
                .build();
    }
}
