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

//        // Preflight요청은 체크하지 않음
//        if(request.getMethod().equals("OPTIONS")){
//            return true;
//        }
//
        String path = request.getRequestURI();

        log.info("check uri.............." + path);
//
//        //api/member/ 경로의 호출은 JWT token 체크하지 않음
//        if(path.startsWith("/user/login/")) {
//            return true;
//        }
//
//        //이미지 조회 경로는 체크하지 않는다면
//        if(path.startsWith("/api/products/view/")) {
//            return true;
//        }
//
        return false;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("------------------------JWTCheckFilter.......................");

        // JWT Authorization 헤더
        String authHeaderStr = request.getHeader("Authorization");

        try {
            // Bearer accestoken -> accesstoken 추출 (문자열에서 'Bearer ' 제거한다는 뜻)
            String accessToken = authHeaderStr.substring(7);
            // accessToken 유효성 검사
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            //filterChain.doFilter(request, response); //이하 추가

            String name = (String) claims.get("name");
            String email = (String) claims.get("email");
            String password = (String) claims.get("pw");
            String nickname = (String) claims.get("nickname");
            // Boolean social = (Boolean) claims.get("social");

            UserDto userDto = new UserDto(name, email, password, nickname);

            log.info("-----------------------------------");
            log.info(userDto);
            log.info(userDto.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDto, password, userDto.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 다음 목적지로 이동
            filterChain.doFilter(request, response);

        }catch(Exception e){

            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();

        }
    }
}
