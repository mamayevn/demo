package kg.asiamotors.demo.service;

import kg.asiamotors.demo.models.User;
import kg.asiamotors.demo.repository.UserRepository;
import kg.asiamotors.demo.utils.PasswordEncoderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRoles("ROLE_USER");
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("testUser");
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("testUser"));
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testAddUser() {
        String encodedPassword = "encodedPassword";
        when(passwordEncoderUtil.encode("password")).thenReturn(encodedPassword);
        User user = new User();
        user.setUsername("newUser");
        user.setPassword(encodedPassword);
        user.setRoles("ROLE_ADMIN");
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.addUser("newUser", "password", "ROLE_ADMIN");
        verify(passwordEncoderUtil, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("newUser", user.getUsername());
        assertEquals(encodedPassword, user.getPassword());
        assertEquals("ROLE_ADMIN", user.getRoles());
    }
}

