package io.falcon.assignment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Payload {
    private String content;
    private Date timestamp;
    private Integer longest_palindrome_size;
}
