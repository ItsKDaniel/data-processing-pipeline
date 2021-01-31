package io.falcon.assignment.model;

import io.falcon.assignment.model.validators.TimeStamp;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class PayloadRequest implements Serializable {
    @NotBlank
    private String content;

    @NotNull
    @TimeStamp
    private String timestamp;
}
