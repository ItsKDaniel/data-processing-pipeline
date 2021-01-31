package io.falcon.assignment.controller;

import io.falcon.assignment.utils.ApiResponseCodes;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<Object> response(ApiResponseCodes response) {
        return ResponseEntity.status(response.getHttpCode())
                .body(response.asResponse());
    }

    protected ResponseEntity<Object> response(ApiResponseCodes response, Object content) {
        return ResponseEntity.status(response.getHttpCode())
                .body(response.asResponse(content));
    }
}
