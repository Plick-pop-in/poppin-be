package com.soongsil.poppin.user.presentation;

import com.soongsil.poppin.user.application.MemberService;
import com.soongsil.poppin.user.application.response.SignupDto;
import com.soongsil.poppin.user.application.response.UserDto;
import com.soongsil.poppin.user.domain.Member;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
public class SignupController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/v1/user/signup")
    public Map<String, String> signup(@RequestBody SignupDto signupDto) {
        log.info("member signup-------------" + signupDto);
        memberService.signupMember(signupDto);

        return Map.of("result", "signup");
    }

    @PostMapping("/v1/user/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> emailMap) {
        String email = emailMap.get("email");
        boolean exists = memberService.checkIfEmailExists(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @PostMapping("/v1/user/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestBody Map<String, String> nicknameMap) {
        String nickname = nicknameMap.get("nickname");
        log.info(nickname);
        boolean exists = memberService.checkIfNicknameExists(nickname);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
