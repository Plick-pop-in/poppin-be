package com.soongsil.poppin.user.application.response;

import com.soongsil.poppin.user.application.exception.JWTException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Map;

public class JWTUtil {
    private static String key = "1234567890123456789012345678901234567890";

    // JWT 문자열 생성, min은 유효시간 검증 시 필요
    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        String jwtStr = Jwts.builder().
                setHeader(Map.of("typ", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();

        return jwtStr;
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;

        try {
            SecretKey Key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));

            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                    .getBody();
        } catch (MalformedJwtException malformedJwtException) {
            throw new JWTException("MalFormed");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JWTException("Expired");
        } catch (InvalidClaimException invalidClaimException) {
            throw new JWTException("Invalid");
        } catch (JwtException jwtException) {
            throw new JWTException("JWTError");
        } catch (Exception e) {
            throw new JWTException("Error");
        }
        return claim;
    }
}
