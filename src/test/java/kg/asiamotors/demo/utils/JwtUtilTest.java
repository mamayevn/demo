package kg.asiamotors.demo.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token, "Token не должно быть равно null");
        assertTrue(token.startsWith("eyJ"), "Token должен начинаться с 'eyJ' (indicating it's a JWT)");
    }

    @Test
    void testExtractUsername() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername, "Username должен быть одинаковыми");
    }

    @Test
    void testValidateToken_ValidToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token);

        assertTrue(isValid, "Token должен соответствовать");
    }

    @Test
    void testValidateToken_ExpiredToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        String expiredToken = token.substring(0, token.lastIndexOf('.')) + ".eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTYzMzg0NTc3MywiZXhwIjoxNjMzODQ2NzgzfQ.DMIH-nHrD1qDBjOG0W6J9w6I6ZQhsk7WhThYPkjwsiM8";
        boolean isValid = jwtUtil.validateToken(expiredToken);

        assertFalse(isValid, "Token должен быть недействительным");
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "invalid.token.string";
        boolean isValid = jwtUtil.validateToken(invalidToken);
        assertFalse(isValid, "Неправильный token должен возвращать ошибку");
    }
}
