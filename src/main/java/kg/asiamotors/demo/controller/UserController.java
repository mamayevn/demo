package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/addUser")
    public String addUser() {
        // Добавляем нового пользователя
        userService.addUser("newuser", "newpassword", "ROLE_USER");

        return "Пользователь добавлен!";
    }
}
