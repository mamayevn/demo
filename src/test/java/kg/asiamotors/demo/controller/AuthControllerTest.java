package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.models.User;
import kg.asiamotors.demo.repository.UserRepository;
import kg.asiamotors.demo.utils.JwtUtil;
import kg.asiamotors.demo.utils.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private PasswordEncoderUtil passwordEncoderUtil;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testLogin() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mockito.mock(UsernamePasswordAuthenticationToken.class));
        when(jwtUtil.generateToken(any(String.class))).thenReturn("mocked-jwt-token");

        String loginRequestJson = """
        {
          "username": "testuser",
          "password": "testpassword"
        }
        """;

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("mocked-jwt-token"));
    }

    @Test
    void testRegister() throws Exception {
        when(passwordEncoderUtil.encode(any(String.class))).thenReturn("encoded-password");

        String registerRequestJson = """
                {
                  "username": "test-user",
                  "password": "test-password"
                }
                """;
        User user = new User();
        user.setUsername("test-user");
        user.setPassword("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(user);
    }
}
