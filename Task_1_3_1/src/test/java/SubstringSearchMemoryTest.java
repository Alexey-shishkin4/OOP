import org.example.SubstringSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SubstringSearchMemoryTest {

    @TempDir
    Path tempDir;

    private File largeTestFile;

    @BeforeEach
    void setUp() throws IOException {
        largeTestFile = tempDir.resolve("largeTestFile.txt").toFile();
        try (FileOutputStream out = new FileOutputStream(largeTestFile)) {
            byte[] data = "a".repeat(1024).getBytes();  // 1 KB блока с символом 'a'
            for (int i = 0; i < 1024 * 1024; i++) {  // 1024 * 1024 KB = 1 GB
                out.write(data);
            }
            out.write("needle".getBytes()); //"needle" в конце файла
        }
    }

    @Test
    void testFindSubstringInLargeFile() throws IOException {
        List<Long> occurrences = SubstringSearch.find(largeTestFile.getAbsolutePath(), "needle");
        assertEquals(List.of(1024L * 1024 * 1024L), occurrences,
                "Expected one occurrence of 'needle' at the end of the file");
    }
}
