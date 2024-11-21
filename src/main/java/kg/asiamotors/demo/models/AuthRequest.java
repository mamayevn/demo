package kg.asiamotors.demo.models;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}