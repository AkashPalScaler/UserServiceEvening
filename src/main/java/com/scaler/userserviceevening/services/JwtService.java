package com.scaler.userserviceevening.services;

import com.scaler.userserviceevening.models.User;
import com.scaler.userserviceevening.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {


    private final SecretKey secretKey;
    private final Long expiryInSeconds;

    public JwtService(@Value("${jwt.secret}") String secretString, @Value("${jwt.expiry-seconds}") Long expiryInSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        this.expiryInSeconds = expiryInSeconds;
    }

    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .issuer("userauthservice")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiryInSeconds * 1000))
                .claim("role", user.getRole().name())
                .signWith(secretKey)
                .compact();
    }

    public Claims validateAndParse(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
