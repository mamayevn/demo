package kg.asiamotors.demo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    // Создаём безопасный ключ
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(
            "0bSnLXcQmADpYTjt3a5Z3qV0RvHK2eT8V-VxwcI2_DdG6cPqMvbQSFh1ONosUnsLdTo".getBytes()
    );

    // Генерация токена
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 часов
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Извлечение имени пользователя из токена
    public String extractUsername(String token) {
        return getClaimFromToken(token).getSubject();
    }

    // Проверка токена на валидность
    public boolean validateToken(String token) {
        Claims claims = getClaimFromToken(token);
        return !claims.getExpiration().before(new Date());
    }

    // Получение всех данных (Claims) из токена
    private Claims getClaimFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}