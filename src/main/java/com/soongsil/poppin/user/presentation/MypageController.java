package com.soongsil.poppin.user.presentation;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.soongsil.poppin.global.response.ResponseDto;
import com.soongsil.poppin.user.application.MemberService;
import com.soongsil.poppin.user.application.PaymentService;
import com.soongsil.poppin.user.application.response.*;
import com.soongsil.poppin.user.domain.Member;
import com.soongsil.poppin.userchat.application.response.JoinUserChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MypageController {
    private final MemberService memberService;
    private final PaymentService paymentService;

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

    @ResponseBody
    @RequestMapping("/v1/verify/{imp_uid}")
    public PaymentDto paymentByImpUid(@PathVariable("imp_uid") String imp_uid)
            throws IamportResponseException, IOException {
        return paymentService.verifyPayment(imp_uid); // 결제 및 검증 DB 값 삽입
    }

    @PostMapping("/v1/user/modify-password")
    public ResponseEntity<String> modifyPassword(@RequestBody String userId) {
        try {
            memberService.modifyPassword(userId);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시 적절한 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호 변경 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/v1/user/set-password")
    public ResponseEntity<String> setPassword(@RequestBody String email) {
        try {
            memberService.setPassword(email);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시 적절한 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호 변경 중 오류가 발생했습니다.");
        }
    }

}