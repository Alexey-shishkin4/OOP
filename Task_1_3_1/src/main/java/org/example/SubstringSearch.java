package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * The class provides a utility method to search for all occurrences.
 */
public class SubstringSearch {

    /**
     * Searches for all occurrences of a substring in text file.
     *
     * @param fileName the name of the file to search
     * @param searchString the substring to search for in the file
     * @return a list of starting positions.
     * @throws IOException if an I/O error occurs when reading the file
     */
    public static List<Long> find(String fileName, String searchString) throws IOException {
        List<Long> occurrences = new ArrayList<>();
        int searchLength = searchString.length();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
                StandardCharsets.UTF_8))) {
            StringBuilder buffer = new StringBuilder();
            long filePosition = 0; // Позиция символов в файле
            int charsRead;

            // Буфер для чтения блоков
            char[] charBuffer = new char[1024 * 1024]; // Читаем файл блоками по 1 МБ символов

            while ((charsRead = reader.read(charBuffer)) != -1) {
                // Добавляем новый блок символов в буфер
                buffer.append(charBuffer, 0, charsRead);

                // Ищем подстроку в текущем буфере
                int index;
                while ((index = buffer.indexOf(searchString)) != -1) {
                    occurrences.add(filePosition + index);
                    buffer.delete(0, index + 1); // Сдвигаем буфер после найденного вхождения
                    filePosition += index + 1;
                }

                // Оставляем только часть строки для перекрытия в следующем блоке
                if (buffer.length() > searchLength - 1) {
                    filePosition += buffer.length() - (searchLength - 1);
                    buffer.delete(0, buffer.length() - (searchLength - 1));
                }
            }
        }
        return occurrences;
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
