package com.soongsil.poppin;

import com.soongsil.poppin.user.domain.Member;
import com.soongsil.poppin.user.domain.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember() {
        Member member = Member.builder()
                .name("user1")
                .email("user1@naver.com")
                .password(passwordEncoder.encode("1111"))
                .nickName("팝팝")
                .build();

        userRepository.save(member);
    }
}
