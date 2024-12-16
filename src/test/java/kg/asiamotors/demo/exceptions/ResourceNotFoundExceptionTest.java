package kg.asiamotors.demo.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceNotFoundExceptionTest {

    @Test
    void testResourceNotFoundExceptionMessage() {
        String errorMessage = "Resource not found";

        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "Сообщение исключения должно совпадать с заданным");
    }

    @Test
    void testResourceNotFoundExceptionThrown() {
        String errorMessage = "Resource not found";

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage(), "Сообщение исключения должно совпадать с заданным");
    }
}
