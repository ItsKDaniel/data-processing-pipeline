package io.falcon.assignment.model;

import io.falcon.assignment.model.validators.TimeStamp;
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
