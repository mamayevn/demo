package kg.asiamotors.demo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileStoragePropertiesTest {

    private FileStorageProperties fileStorageProperties;

    @BeforeEach
    void setUp() {
        fileStorageProperties = new FileStorageProperties();
    }

    @Test
    void testGetAndSetUploadDir() {
        String expectedUploadDir = "uploads";
        fileStorageProperties.setUploadDir(expectedUploadDir);
        assertNotNull(fileStorageProperties.getUploadDir(), "UploadDir не должен быть null");
        assertEquals(expectedUploadDir, fileStorageProperties.getUploadDir(), "UploadDir должен совпадать с ожидаемым значением");
    }
}
