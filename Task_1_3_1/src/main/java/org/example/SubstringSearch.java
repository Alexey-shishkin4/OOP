package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * The class provides a utility method to search for all occurrences of a substring in a text file.
 */
public class SubstringSearch {

    /**
     * Searches for all occurrences of a substring in a text file.
     *
     * @param fileName the name of the file to search
     * @param searchString the substring to search for in the file
     * @return a list of starting positions of each occurrence of the substring
     * @throws IOException if an I/O error occurs when reading the file
     */
    public static List<Long> find(String fileName, String searchString) throws IOException {
        List<Long> occurrences = new ArrayList<>();
        int searchLength = searchString.length();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
                StandardCharsets.UTF_8))) {
            StringBuilder buffer = new StringBuilder();
            long filePosition = 0; // Character position in the file
            int charsRead;

            // Buffer for reading chunks
            char[] charBuffer = new char[1024 * 1024]; // Read the file in 1 MB chunks

            while ((charsRead = reader.read(charBuffer)) != -1) {
                // Add the new chunk of characters to the buffer
                buffer.append(charBuffer, 0, charsRead);

                // Find substring within the current buffer
                int index;
                while ((index = indexOf(buffer, searchString)) != -1) {
                    occurrences.add(filePosition + index);
                    buffer.delete(0, index + 1); // Shift buffer past the found occurrence
                    filePosition += index + 1;
                }

                // Keep only the overlap part of the buffer for the next read
                if (buffer.length() > searchLength - 1) {
                    filePosition += buffer.length() - (searchLength - 1);
                    buffer.delete(0, buffer.length() - (searchLength - 1));
                }
            }
        }
        return occurrences;
    }

    /**
     * Custom implementation of the indexOf method.
     *
     * @param text the text in which to search
     * @param pattern the substring to search for
     * @return the index of the first occurrence of pattern in text, or -1 if pattern is not found
     */
    private static int indexOf(StringBuilder text, String pattern) {
        int[] lps = buildLPSArray(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                return i - j; // Match found
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1; // No match found
    }

    /**
     * Builds the longest prefix suffix (LPS) array for the KMP algorithm.
     *
     * @param pattern the substring for which to build the LPS array
     * @return the LPS array
     */
    private static int[] buildLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0; // Length of the previous longest prefix suffix
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * Main method for testing the substring search functionality.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String fileName = "input.txt";
        String searchString = "bra";

        try {
            List<Long> positions = find(fileName, searchString);
            System.out.println(positions);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
