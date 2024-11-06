import org.example.SubstringSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringSearchTest {

    @TempDir
    Path tempDir;

    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        // Создаем временный файл перед каждым тестом
        testFile = tempDir.resolve("testFile.txt").toFile();
    }

    @Test
    void testFindSubstringSingleOccurrence() throws IOException {
        writeToFile(testFile, "abrakadabra");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "bra");
        assertEquals(List.of(1L, 8L), occurrences);
    }

    @Test
    void testFindSubstringMultipleOccurrences() throws IOException {
        writeToFile(testFile, "ababababa");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "aba");
        assertEquals(List.of(0L, 2L, 4L, 6L), occurrences);
    }

    @Test
    void testFindSubstringAtStartAndEnd() throws IOException {
        writeToFile(testFile, "test...test");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "test");
        assertEquals(List.of(0L, 7L), occurrences);
    }

    @Test
    void testFindSubstringWithCyrillicCharacters() throws IOException {
        writeToFile(testFile, "абракадабра");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "бра");
        assertEquals(List.of(1L, 8L), occurrences);
    }

    @Test
    void testFindSubstringNonExisting() throws IOException {
        writeToFile(testFile, "hello world");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "test");
        assertEquals(List.of(), occurrences);
    }

    @Test
    void testFindSubstringInLargeFile() throws IOException {
        try (FileWriter writer = new FileWriter(testFile)) {
            for (int i = 0; i < 10000; i++) {
                writer.write("abra");
            }
            writer.write("kadabra");
        }

        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "kadabra");
        assertEquals(List.of(40000L), occurrences);
    }

    @Test
    void testFindSubstringInEmptyFile() throws IOException {
        writeToFile(testFile, "");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "any");
        assertEquals(List.of(), occurrences, "Expected no matches in an empty file");
    }

    @Test
    void testFindSingleCharacterSubstring() throws IOException {
        writeToFile(testFile, "aaaaaa");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "a");
        assertEquals(List.of(0L, 1L, 2L, 3L, 4L, 5L), occurrences, "Expected occurrences at each position");
    }

    @Test
    void testFindSubstringLongerThanText() throws IOException {
        writeToFile(testFile, "short");
        List<Long> occurrences = SubstringSearch.find(testFile.getAbsolutePath(), "longsubstring");
        assertEquals(List.of(), occurrences, "Expected no matches when the substring is longer than the text");
    }

    /**
     * Вспомогательный метод для записи текста в файл.
     */
    private void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }
}
