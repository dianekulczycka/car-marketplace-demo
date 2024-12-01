package org.example.postlistingservice.utilities;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Component

public class JwtUtility {

    @Value("${jwt.secret}")
    private String secret;

    private JwtParser jwtParser;

    @PostConstruct
    public void setUpKey() {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        String AUTH_HEADER_PREFIX = "Bearer ";
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            return authHeader.substring(AUTH_HEADER_PREFIX.length());
        }

        throw new IllegalArgumentException("invalid auth header");
    }

    public String extractUsernameFromToken(String token) {
        return extractFromToken(token, Claims::getSubject);
    }

    public String extractRoleFromToken(String token) {
        return extractFromToken(token, claims -> {
            List<String> roles = (List<String>) claims.get("roles");
            return roles != null ? roles.get(0) : null;
        });
    }

    public boolean isTokenExpired(String token) {
        Date expiredAt = extractFromToken(token, Claims::getExpiration);
        return !expiredAt.after(new Date());
    }

    public <T> T extractFromToken(String token, Function<Claims, T> extractor) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return extractor.apply(claims);
    }
}
