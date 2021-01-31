package io.falcon.assignment.common.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponse<T> {
    private String status;
    private T content;
}
