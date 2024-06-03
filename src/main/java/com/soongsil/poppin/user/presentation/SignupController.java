package com.soongsil.poppin.user.presentation;

import com.soongsil.poppin.user.application.MemberService;
import com.soongsil.poppin.user.application.response.UserDto;
import com.soongsil.poppin.user.domain.Member;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
public class SignupController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/v1/signup")
    public Map<String, String> signup(@RequestBody UserDto userDto) {
        log.info("member signup-------------" + userDto);
        memberService.signupMember(userDto);

        return Map.of("result", "signup");
    }
}
