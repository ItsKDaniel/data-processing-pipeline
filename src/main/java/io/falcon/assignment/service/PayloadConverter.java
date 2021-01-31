package io.falcon.assignment.service;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PayloadConverter {

    public Payload makePayload(PayloadRequest message) {
        return Payload.builder()
                .content(message.getContent())
                .timestamp(convertTimeStampToGMT(message.getTimestamp()))
                .longest_palindrome_size(calculateLongestPalindromeSize(message.getContent()))
                .build();
    }

    private String convertTimeStampToGMT(String timestamp) {
        return null;
    }

    private int calculateLongestPalindromeSize(String content) {
        return 0;
    }
}
