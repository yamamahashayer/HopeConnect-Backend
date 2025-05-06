package com.example.HopeConnect.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private static final String SECRET = "mysecretkeyforhopeconnectmysecretkey";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public String generateToken(String email, String userType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", userType);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUserType(String token) {
        return (String) extractAllClaims(token).get("userType");
    }

    public boolean isTokenValid(String token) {
        return !extractAllClaims(token).getExpiration().before(new Date());
    }
}
