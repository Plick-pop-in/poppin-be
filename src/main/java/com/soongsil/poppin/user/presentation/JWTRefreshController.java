package com.soongsil.poppin.user.presentation;

import com.soongsil.poppin.user.application.exception.JWTException;
import com.soongsil.poppin.user.application.response.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class JWTRefreshController {
    // refresh 토큰을 이용한 토큰 갱신
    @RequestMapping("/user/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader,
                                       String refreshToken) {
        // accesstoken 없을 때
        if(authHeader == null || authHeader.length() < 7) {
            throw new JWTException("INVALID STRING");
        }

        // refreshtoken 없을 때
        if(refreshToken == null) {
            throw new JWTException("NULL_REFRESH");
        }

        // authHeader에서 앞에 일곱 글자(Bearer )잘라내면 accessToken만 남는다.
        String accessToken = authHeader.substring(7);

        // accessToken 만료 확인
        // 만료되지 않은 경우
        if(checkExpiredToken(accessToken) == false) {
            // 원래대로 돌려보낸다.
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // refreshToken 확인
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        log.info("refresh claims: " + claims);

        // 문제 없으면 새로운 accessToken으로 교체한다.
        String newAccessToken = JWTUtil.generateToken(claims, 10);
        // refreshToken이 1시간 이내로 남았으면 새로운 refreshToken으로 교체한다.
        String newRefreshToken = checkTime((Integer)claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    // accessToken 만료 확인 함수
    // 1시간 미만으로 남았다면
    private boolean checkTime(Integer exp) {
        // JWT exp를 날짜로 변환
        java.util.Date expDate = new java.util.Date((long)exp * (1000));

        // 현재 시간과의 차이 계산 (ms)
        long gap = expDate.getTime() - System.currentTimeMillis();

        // 분단위로 계산
        long leftMin = gap / (1000 * 60);

        return leftMin < 60;
    }

    // 토큰 만료 여부 확인
    private boolean checkExpiredToken(String token) {
        try {
            JWTUtil.validateToken(token);
        } catch (JWTException e) {
            // Expired == 만료됐다는 메시지 -> 새로 로그인 해야 됨.
            if(e.getMessage().equals("Expired")) {
                return true;
            }
        }
        return false;
    }
}
