package io.falcon.assignment.service.helper;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Enriches the payload to be persisted to Redis with
 * - timestamp in UTC zone.
 * - longest palindrome size of the request string
 */
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
                .longestPalindromeSize(helper.calculateLongestPalindromeSize(message.getContent()))
                .build();
    }

    // Convert the zoned timestamp to UTC zone
    // Ex: "2021-01-31 04:42:40+0400" to "2021-01-31 00:42:40+0000"
    private String convertTimeStampToUTC(String timestamp) {
        return OffsetDateTime.parse(timestamp, formatter)
                .withOffsetSameInstant(ZoneOffset.UTC)
                .format(formatter);
    }

}
