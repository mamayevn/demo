package kg.asiamotors.demo.config;

import kg.asiamotors.demo.filters.JwtAuthenticationFilter;
import kg.asiamotors.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class SecurityConfigTest {

    private SecurityConfig securityConfig;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private UserService userService;
    @Autowired
    private SecurityFilterChain securityFilterChain;

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = mock(JwtAuthenticationFilter.class);
        userService = mock(UserService.class);
        securityConfig = new SecurityConfig(jwtAuthenticationFilter, userService);
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder, "PasswordEncoder не должен быть null");
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration configuration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(configuration.getAuthenticationManager()).thenReturn(authenticationManager);

        AuthenticationManager result = securityConfig.authenticationManager(configuration);
        assertNotNull(result, "AuthenticationManager не должен быть null");
    }
}
