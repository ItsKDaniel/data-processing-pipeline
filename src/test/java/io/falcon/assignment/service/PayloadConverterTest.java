package io.falcon.assignment.service;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PayloadConverterTest {

    private static PayloadConverter converter;
    private static List<PayloadRequest> payloads;

    @BeforeAll
    public static void init() {
        converter = new PayloadConverter(new PalindromeHelper());

        PayloadRequest payload_1 = buildPayload("daniel", "2021-01-31 10:12:40+0530");
        PayloadRequest payload_2 = buildPayload("abrakadabra", "2018-10-09 00:12:12+0100");
        PayloadRequest payload_3 = buildPayload("race car", "2021-01-31 09:12:19+1000");
        PayloadRequest payload_4 = buildPayload("mA D 2323 a ! m", "2021-02-01 00:12:12-0500");
        PayloadRequest payload_5 = buildPayload("12312313", "2021-01-31 12:12:12-1100");
        PayloadRequest payload_6 = buildPayload("not hin g", "2021-01-09 00:12:12+0300");
        PayloadRequest payload_7 = buildPayload("aa bb !! b b a a", "2021-01-22 12:15:12-0800");

        payloads = Arrays.asList(payload_1, payload_2, payload_3, payload_4, payload_5, payload_6, payload_7);
    }

    private static PayloadRequest buildPayload(String content, String timestamp) {
        PayloadRequest request = new PayloadRequest();
        request.setContent(content);
        request.setTimestamp(timestamp);
        return request;
    }

    @Test
    void makePayload() {
        List<Payload> expected = Arrays.asList(
                Payload.builder().content("daniel").timestamp("2021-01-31 04:42:40+0000").longest_palindrome_size(0).build(),
                Payload.builder().content("abrakadabra").timestamp("2018-10-08 23:12:12+0000").longest_palindrome_size(3).build(),
                Payload.builder().content("race car").timestamp("2021-01-30 23:12:19+0000").longest_palindrome_size(7).build(),
                Payload.builder().content("mA D 2323 a ! m").timestamp("2021-02-01 05:12:12+0000").longest_palindrome_size(5).build(),
                Payload.builder().content("12312313").timestamp("2021-01-31 23:12:12+0000").longest_palindrome_size(0).build(),
                Payload.builder().content("not hin g").timestamp("2021-01-08 21:12:12+0000").longest_palindrome_size(0).build(),
                Payload.builder().content("aa bb !! b b a a").timestamp("2021-01-22 20:15:12+0000").longest_palindrome_size(8).build()
        );

        List<Payload> actual = payloads.stream()
                .map(payload -> converter.makePayload(payload))
                .collect(Collectors.toList());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }

    }
}
