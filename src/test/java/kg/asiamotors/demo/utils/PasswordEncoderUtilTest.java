package kg.asiamotors.demo.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderUtilTest {

    private PasswordEncoderUtil passwordEncoderUtil;

    @BeforeEach
    void setUp() {
        passwordEncoderUtil = new PasswordEncoderUtil();
    }

    @Test
    void testEncode() {
        String rawPassword = "mySecretPassword";

        String encodedPassword = passwordEncoderUtil.encode(rawPassword);

        assertNotNull(encodedPassword, "Encoded пароль не должен равен null");
        assertNotEquals(rawPassword, encodedPassword, "Encoded пароль не должен совпадать с необработаным");
    }

    @Test
    void testMatches_ValidPassword() {
        String rawPassword = "mySecretPassword";
        String encodedPassword = passwordEncoderUtil.encode(rawPassword);

        boolean matches = passwordEncoderUtil.matches(rawPassword, encodedPassword);

        assertTrue(matches, "Password должен совпадать с encoded password");
    }

    @Test
    void testMatches_InvalidPassword() {
        String rawPassword = "mySecretPassword";
        String encodedPassword = passwordEncoderUtil.encode(rawPassword);

        boolean matches = passwordEncoderUtil.matches("wrongPassword", encodedPassword);

        assertFalse(matches, "Password не должен совпадать если исходный пароль не верен");
    }
}
