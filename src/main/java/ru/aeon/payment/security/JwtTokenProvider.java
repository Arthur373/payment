package ru.aeon.payment.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.aeon.payment.exceptions.JwtAuthenticationException;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * Provider for token generator and validator.
 *
 * @author Arthur
 * @version 1.0
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secret;
    @Value("${jwt.expiredDate}")
    private long validityInSeconds;


    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public String generateToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token, @Nullable String username) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return ((username == null || claimsJws.getBody().getSubject().equals(username))
                    && !claimsJws.getBody().getExpiration().before(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }
}
