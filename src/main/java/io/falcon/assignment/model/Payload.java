package io.falcon.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class Payload {
    private String content;
    private String timestamp;
    @JsonProperty(value = "longest_palindrome_size")
    private Integer longestPalindromeSize;

    public Payload() {
        // added for serialization
    }
}
