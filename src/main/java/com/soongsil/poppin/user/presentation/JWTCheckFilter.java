package com.soongsil.poppin.user.presentation;

import com.google.gson.Gson;
import com.soongsil.poppin.user.application.response.JWTUtil;
import com.soongsil.poppin.user.application.response.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // Preflight요청은 체크하지 않음
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }

        String path = request.getRequestURI();

        log.info("check uri.............." + path);

        //user/login 경로의 호출은 JWT token 체크하지 않음
        if(path.startsWith("/v1/user/login")) {
            return true;
        }

//        //이미지 조회 경로는 체크하지 않는다면
//        if(path.startsWith("/api/products/view/")) {
//            return true;
//        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            try {
                System.out.println("Validating token: " + token); // 디버깅 정보 추가
                Map<String, Object> claims = JWTUtil.validateToken(token);
                System.out.println("JWT claims: " + claims);

                Long id = (Long) claims.get("id");
                String name = (String) claims.get("name");
                String email = (String) claims.get("email");
                String password = (String) claims.get("password");
                String nickname = (String) claims.get("nickname");
                boolean social = (boolean) claims.get("social");

                UserDto userDto = new UserDto(id, name, email, password, nickname, social);

                log.info("-----------------------------------");
                log.info(userDto);
                log.info(userDto.getAuthorities());

                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDto, password, userDto.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // 다음 목적지로 이동
                chain.doFilter(request, response);

                // 사용자 인증 설정 (예시)
                // 예: SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JWTUtil.JWTException e) {
                // 구체적인 예외 메시지 출력
                System.err.println("JWT Check Error: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            } catch (Exception e) {
                // 다른 예외 처리
                System.err.println("General Error: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "An error occurred while processing the JWT");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
