package kg.asiamotors.demo.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EntityNotFoundExceptionTest {

    @Test
    void testEntityNotFoundExceptionMessage() {
        String expectedMessage = "Entity not found";

        EntityNotFoundException exception = new EntityNotFoundException(expectedMessage);

        assertNotNull(exception.getMessage(), "Сообщение исключения не должно быть null");
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение исключения должно совпадать с ожидаемым");
    }
}
