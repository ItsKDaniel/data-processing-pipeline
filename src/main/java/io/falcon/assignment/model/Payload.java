package io.falcon.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@Data
@Builder
public class Payload {
    private String content;
    private String timestamp;
    private Integer longest_palindrome_size;

    public Payload() {
    }
}
