package io.falcon.assignment.common.model;

import io.falcon.assignment.common.model.validators.TimeStamp;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class Payload implements Serializable {

    @NotBlank
    private String content;

    @TimeStamp
    private String timestamp;

    private Integer longest_palindrome_size;
}
