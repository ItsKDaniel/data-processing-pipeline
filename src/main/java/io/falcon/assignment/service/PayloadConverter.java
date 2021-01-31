package io.falcon.assignment.service;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class PayloadConverter {

    private final DateTimeFormatter formatter;
    private final PalindromeHelper helper;

    @Autowired
    public PayloadConverter(PalindromeHelper helper) {
        this.formatter = DateTimeFormatter.ofPattern(Constants.TIMESTAMP_WITH_ZONE);
        this.helper = helper;
    }

    public Payload makePayload(PayloadRequest message) {
        return Payload.builder()
                .content(message.getContent())
                .timestamp(convertTimeStampToUTC(message.getTimestamp()))
                .longest_palindrome_size(helper.calculateLongestPalindromeSize(message.getContent()))
                .build();
    }

    private String convertTimeStampToUTC(String timestamp) {
        return OffsetDateTime.parse(timestamp, formatter)
                .withOffsetSameInstant(ZoneOffset.UTC)
                .format(formatter);
    }

}
