package kg.asiamotors.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageApiServiceTest {

    private FileStorageApiService fileStorageApiService;

    @BeforeEach
    void setUp() throws IOException {
        Path tempDirectory = Files.createTempDirectory("uploads");
        fileStorageApiService = new FileStorageApiService() {
            @Override
            public Path getFileStorageLocation() {
                return tempDirectory;
            }
        };
    }


    @Test
    void testStoreFile_success() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "testFile.txt", "text/plain", "Test file content".getBytes());

        String storedFileName = fileStorageApiService.storeFile(mockFile);

        Path filePath = fileStorageApiService.getFileStorageLocation().resolve(storedFileName);
        System.out.println("Expected file path: " + filePath);
    }


    @Test
    void testStoreFile_invalidName() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "../testFile.txt", "text/plain", "Test file content".getBytes());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileStorageApiService.storeFile(mockFile));

        assertTrue(exception.getMessage().contains(".."),
                "Сообщение об ошибке должно содержать информацию о недопустимом имени файла");
    }
}
