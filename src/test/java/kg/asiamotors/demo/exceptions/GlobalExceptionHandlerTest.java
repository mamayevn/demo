package kg.asiamotors.demo.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");
        Model model = new ConcurrentModel();

        String viewName = exceptionHandler.handleBrandNotFoundException(exception, model);

        assertEquals("error_page", viewName, "Должна быть возвращена ошибка error_page");
        assertEquals("Entity not found", model.getAttribute("errorMessage"), "Сообщение об ошибке должно совпадать");
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleResourceNotFoundException(exception);

        assertNotNull(response, "Ответ не должен быть null");
        assertEquals(404, response.getStatusCodeValue(), "HTTP статус должен быть 404");
        assertNotNull(response.getBody(), "Тело ответа не должно быть null");
        assertEquals("Resource not found", response.getBody().get("message"), "Сообщение должно совпадать");
        assertEquals(404, response.getBody().get("status"), "Статус должен быть 404");
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Generic error");
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGenericException(exception);
        assertNotNull(response, "Ответ не должен быть null");
        assertEquals(500, response.getStatusCodeValue(), "HTTP статус должен быть 500");
        assertNotNull(response.getBody(), "Тело ответа не должно быть null");
        assertEquals("Произошла ошибка: Generic error", response.getBody().get("message"), "Сообщение должно совпадать");
        assertEquals(500, response.getBody().get("status"), "Статус должен быть 500");
    }
}
