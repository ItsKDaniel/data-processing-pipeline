package io.falcon.assignment.service;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.falcon.assignment.utils.Constants.MATCH_ONLY_ALPHABETS;

@Component
public class PalindromeHelper {

    private final Pattern pattern;

    public PalindromeHelper() {
        this.pattern = Pattern.compile(MATCH_ONLY_ALPHABETS);
    }

    /**
     * Uses Manacher's algorithm to calculate the longest palindromic string
     * and returns its length
     *
     * @param content original string
     * @return length of the longest palindrome in the original string
     */
    public int calculateLongestPalindromeSize(String content) {
        String filtered = filterAlphabets(content);

        if (filtered.length() <= 1) {
            return filtered.length();
        }

        int length = longestPalindromicSubstring(filtered).length();
        return length > 1 ? length : 0;
    }

    /**
     * Filters the original string for alphabets, and returns the final String in lowercase
     * Ex: "Abr a !ka daBR@a" to "AbrakadaBRa" to "abrakadabra"
     *
     * @param content original string
     * @return transformed string in lowercase
     */
    private String filterAlphabets(String content) {
        return pattern.matcher(content)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining())
                .toLowerCase(Locale.US);
    }

    // More info on the algo here
    //  https://www.baeldung.com/cs/manachers-algorithm
    //  https://leetcode.com/problems/longest-palindromic-substring/solution/
    //  https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
    private String longestPalindromicSubstring(String content) {
        // transform
        char[] transformed = transformToManacherArray(content);

        // each element of palindrome_length will contain the length of the longest palindromic substring of the transformed text
        // with center as the current index.
        int[] palindrome_length = new int[transformed.length];

        // Manacher's algo for odd length palindrome
        int center = 0, right = 0;

        // starting with index 1 as $ is at 0 for bound checking
        for (int i = 1; i < transformed.length - 1; i++) {
            int mirror = 2 * center - i;

            if (right > i) {
                palindrome_length[i] = Math.min(right - i, palindrome_length[mirror]);
            }

            // expand and verify the palindrome with center at index i
            // say i is at 'a' in the string "$#m#a#d#a#m#%"
            // t[4 + (1 + 0)] == t[4 - (1 + 0)] => t[5] == t[3] => # == # => p[4] = 1
            // t[4 + (1 + 1)] == t[4 - (1 + 1)] => t[6] == t[2] => m == d => break
            while (transformed[i + (1 + palindrome_length[i])] == transformed[i - (1 + palindrome_length[i])]) {
                palindrome_length[i]++;
            }

            // if palindrome centered at i expands past right,
            // adjust center based on expanded palindrome.
            if (palindrome_length[i] + i > right) {
                center = i;
                right = i + palindrome_length[i];
            }
        }

        // iterate thru palindrome_length
        // find the appropriate center and the length required to get the longest palindrome substring
        int length = 0;
        center = 0;
        for (int i = 1; i < palindrome_length.length - 1; i++) {
            if (palindrome_length[i] > length) {
                length = palindrome_length[i];
                center = i;
            }
        }

        return content.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
    }

    // Transforms the current string
    // "madam" --> "$#m#a#d#a#m#%"
    // $ and % denote the start and end of the array for bounds check
    // # are interleaved between each character to consider them as odd length palindromes.
    private char[] transformToManacherArray(String content) {
        char[] arr = new char[content.length() * 2 + 3];
        arr[0] = '$';
        arr[content.length() * 2 + 2] = '%';
        for (int i = 0; i < content.length(); i++) {
            arr[2 * i + 1] = '#';
            arr[2 * i + 2] = content.charAt(i);
        }
        arr[content.length() * 2 + 1] = '#';

        return arr;
    }

}
