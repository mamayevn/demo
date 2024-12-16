package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void addUser_ShouldReturnSuccessMessage() {
        String response = userController.addUser();
        assertEquals("Пользователь добавлен!", response);
        verify(userService, times(1)).addUser("newuser", "newpassword", "ROLE_USER");
    }
}
