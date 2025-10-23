package org.ozbeman.ebento.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Map;


@Service
public class JwtTokenService {
    private static final String SECRET_KEY = "bcfcbb8dd68213ca94fe17fe297d12cea81105c9c38d75b75c7d0d5816f7798c";


    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String sessionId, Map<String, Object> payload) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder().claims(payload).subject(sessionId).signWith(secretKey).compact();
    }

    private Claims extractClaims(String token) {
        SecretKey secretKey = getSecretKey();
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public String extractSessionId(String token) {
        return extractClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractClaims(token);
        List<?> extractedRoles = claims.get("roles", List.class);
        return extractedRoles.stream().map(String::valueOf).toList();
    }

    public boolean isValid(String token) {
        Claims claims = extractClaims(token);
        return true;
    }
}
