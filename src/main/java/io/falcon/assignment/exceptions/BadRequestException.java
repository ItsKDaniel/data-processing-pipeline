package io.falcon.assignment.exceptions;

import io.falcon.assignment.utils.ApiResponseCodes;

public class BadRequestException extends RuntimeException {

    private final ApiResponseCodes errorCode;

    private BadRequestException(ApiResponseCodes errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public static BadRequestException invalidDate() {
        return new BadRequestException(ApiResponseCodes.INVALID_DATE_FORMAT);
    }

    public ApiResponseCodes getErrorCode() {
        return errorCode;
    }
}
