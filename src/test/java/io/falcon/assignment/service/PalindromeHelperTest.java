package io.falcon.assignment.service;

import io.falcon.assignment.service.helper.PalindromeHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromeHelperTest {

    private static PalindromeHelper tested;

    @BeforeAll
    static void setUp() {
        tested = new PalindromeHelper();
    }

    @Test
    void calculateLongestPalindromeSize() {
        List<Integer> actual = Stream.of("daniel", "abrakadabra", "race car", "mA D 2323 a ! m", "12312313", "not hin g", "aa bb !! b b a a")
                .map(content -> tested.calculateLongestPalindromeSize(content))
                .collect(Collectors.toList());

        List<Integer> expected = Arrays.asList(0, 3, 7, 5, 0, 0, 8);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}
