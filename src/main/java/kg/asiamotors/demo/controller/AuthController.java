package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.models.AuthRequest;
import kg.asiamotors.demo.models.User;
import kg.asiamotors.demo.repository.UserRepository;
import kg.asiamotors.demo.utils.JwtUtil;
import kg.asiamotors.demo.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        return jwtUtil.generateToken(authRequest.getUsername());
    }
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        String encodedPassword = passwordEncoderUtil.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "Пользователь зарегистрирован!";
    }
}