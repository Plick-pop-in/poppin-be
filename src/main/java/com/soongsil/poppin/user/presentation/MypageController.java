package com.soongsil.poppin.user.presentation;

import com.soongsil.poppin.user.application.MemberService;
import com.soongsil.poppin.user.application.response.ChargePointDto;
import com.soongsil.poppin.user.application.response.JWTUtil;
import com.soongsil.poppin.user.application.response.MypageDto;
import com.soongsil.poppin.user.application.response.UserDto;
import com.soongsil.poppin.user.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MypageController {
    private final MemberService memberService;

    @GetMapping("/v1/user/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken) {
        log.info("accessToken: " + accessToken);
        UserDto userDto = memberService.getKakaoMember(accessToken);

        Map<String, Object> claims = userDto.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60*10);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }

    @PutMapping("/v1/user/modify-nickname")
    public ResponseEntity<Boolean> modify (@RequestBody MypageDto mypageDto) {
        Boolean check = memberService.modifyNickname(mypageDto);
        return ResponseEntity.ok(check);
    }

    @PutMapping("/v1/user/charge-point")
    public ResponseEntity<Long> chargePoint(@RequestBody ChargePointDto chargePointDto) {
        Long newPoint = memberService.chargePoint(chargePointDto);
        if (newPoint == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(newPoint);
    }

}