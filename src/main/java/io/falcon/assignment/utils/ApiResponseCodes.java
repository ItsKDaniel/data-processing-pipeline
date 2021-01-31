package io.falcon.assignment.utils;

import io.falcon.assignment.model.ApiResponse;

public enum ApiResponseCodes {
    // GENERICS
    SUCCESS(200, 0, null),
    BAD_REQUEST(400, 0, null),
    INTERNAL_SERVER_ERROR(500, 0, null),

    PAYLOAD_PUBLISHED(201, 10, "payload published successfully"),

    FORM_CONSTRAINT_ERROR(400, 1, "invalid or missing request elements"),
    INVALID_DATE_FORMAT(400, 5, "invalid date format");

    private final int httpCode;
    private final int opCode;
    private final String description;

    ApiResponseCodes(int httpCode, int opCode, String description) {
        this.httpCode = httpCode;
        this.opCode = opCode;
        this.description = description;
    }

    public ApiResponse<String> asResponse() {
        return ApiResponse.<String>builder()
                .status(status())
                .content(this.description)
                .build();
    }

    public <T> ApiResponse<T> asResponse(T content) {
        return ApiResponse.<T>builder()
                .status(status())
                .content(content)
                .build();
    }

    private String status() {
        return String.format("%1$03d.%2$02d", this.httpCode, this.opCode);
    }

    public int getHttpCode() {
        return httpCode;
    }

    public int getOpCode() {
        return opCode;
    }

    public String getDescription() {
        return description;
    }
}
