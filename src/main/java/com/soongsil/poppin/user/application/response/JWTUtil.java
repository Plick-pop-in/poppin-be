package com.soongsil.poppin.user.application.response;

import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class JWTUtil {
    private static final String SECRET_KEY = "0123456789012345678901234567890123456789"; // 최소 32바이트

    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey key;

        try {
            key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("Error creating SecretKey: " + e.getMessage());
        }

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
    }

    public static Map<String, Object> validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new JWTException("Invalid token: Token is null or empty");
        }

        Map<String, Object> claims;

        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes("UTF-8"));

            System.out.println("SecretKey: " + key); // 디버깅 정보 추가

            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("Malformed JWT token: " + malformedJwtException.getMessage());
            throw new JWTException("MalFormed: " + malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            System.err.println("Expired JWT token: " + expiredJwtException.getMessage());
            throw new JWTException("Expired: " + expiredJwtException.getMessage());
        } catch (InvalidClaimException invalidClaimException) {
            System.err.println("Invalid claims in JWT token: " + invalidClaimException.getMessage());
            throw new JWTException("Invalid: " + invalidClaimException.getMessage());
        } catch (JwtException jwtException) {
            System.err.println("JWT processing error: " + jwtException.getMessage());
            throw new JWTException("JWTError: " + jwtException.getMessage());
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
            throw new JWTException("Error: " + e.getMessage());
        }

        return claims;
    }

    public static class JWTException extends RuntimeException {
        public JWTException(String message) {
            super(message);
        }
    }
}
