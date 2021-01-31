package io.falcon.assignment.common.exceptions;

import io.falcon.assignment.common.utils.ApiResponseCodes;

public class BadRequestException extends RuntimeException {

    private final ApiResponseCodes errorCode;

    private BadRequestException(ApiResponseCodes errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    private BadRequestException(ApiResponseCodes errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public static BadRequestException invalidDate() {
        return new BadRequestException(ApiResponseCodes.INVALID_DATE_FORMAT);
    }

    public static BadRequestException invalidDate(Exception ex) {
        return new BadRequestException(ApiResponseCodes.INVALID_DATE_FORMAT, ex);
    }

    public ApiResponseCodes getErrorCode() {
        return errorCode;
    }
}
