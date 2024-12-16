package kg.asiamotors.demo.filters;

import kg.asiamotors.demo.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil);
        SecurityContextHolder.clearContext(); // Сброс контекста перед каждым тестом
    }

    @Test
    void testDoFilterInternal_ValidJwt() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer valid.jwt.token");
        when(jwtUtil.extractUsername("valid.jwt.token")).thenReturn("testuser");
        when(jwtUtil.validateToken("valid.jwt.token")).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(1)).extractUsername("valid.jwt.token");
        verify(jwtUtil, times(1)).validateToken("valid.jwt.token");
        verify(filterChain, times(1)).doFilter(request, response);

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        assert authentication.getPrincipal().equals("testuser");
    }

    @Test
    void testDoFilterInternal_NoAuthHeader() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, never()).extractUsername(anyString());
        verify(jwtUtil, never()).validateToken(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_InvalidJwt() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer invalid.jwt.token");
        when(jwtUtil.extractUsername("invalid.jwt.token")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(1)).extractUsername("invalid.jwt.token");
        verify(jwtUtil, never()).validateToken(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_LoginEndpoint() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setRequestURI("/login");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, never()).extractUsername(anyString());
        verify(jwtUtil, never()).validateToken(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
