package com.soongsil.poppin.user.presentation;

import com.google.gson.Gson;
import com.soongsil.poppin.user.application.response.JWTUtil;
import com.soongsil.poppin.user.application.response.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인 인증 성공 시 상황
        log.info("--------------onAuthenticationSuccess--------------");
        log.info(authentication);
        log.info("---------------------------------------------------");

        // UserDto에서 정보 받아온다.
        UserDto userDto = (UserDto) authentication.getPrincipal();

        Map<String, Object> claims = userDto.getClaims();

        String accessToken = JWTUtil.generateToken(claims, 10);
        String refreshToken = JWTUtil.generateToken(claims, 60*24);

        // Token 정보 생성
        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        // JSON 객체 생성
        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");

        // 출력
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
