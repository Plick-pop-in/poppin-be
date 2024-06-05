package com.soongsil.poppin.user.application;

import com.soongsil.poppin.global.response.ErrorCode;
import com.soongsil.poppin.popup.application.exception.PopupException;
import com.soongsil.poppin.user.application.exception.UserException;
import com.soongsil.poppin.user.application.response.ChargePointDto;
import com.soongsil.poppin.user.application.response.MypageDto;
import com.soongsil.poppin.user.application.response.SignupDto;
import com.soongsil.poppin.user.application.response.UserDto;
import com.soongsil.poppin.user.domain.Member;
import com.soongsil.poppin.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.LinkedHashMap;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    UserDto entityToDTO(Member member) {

        UserDto dto = new UserDto(
                member.getUserId(),
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getNickName(),
                member.isSocial(),
                member.getPoint());

        return dto;
    }

    public void signupMember(SignupDto signupDto) {
        log.info("회원가입============");


        Member member = Member.builder()
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .nickName(signupDto.getNickname())
                .point(0L)
                .build();

        userRepository.save(member);
    }

    public boolean checkIfEmailExists(String email) {
        return userRepository.getWithEmail(email) != null;
    }

    public boolean checkIfNicknameExists(String nickname) {
        return userRepository.findBynickName(nickname) != null;
    }

    public Boolean modifyNickname(MypageDto mypageDto) {
        Optional<Member> result = userRepository.findById(mypageDto.getId());
        Member check = userRepository.findBynickName(mypageDto.getNickname());

        Member member = result.orElseThrow(() ->  new UserException(ErrorCode.USER_NOT_FOUND));
        if(member.getNickName().equals(mypageDto.getNickname())) {
            return null;
        }

        if(check == null) {
            member.setNickName(mypageDto.getNickname());
            userRepository.save(member);
            return true;
        }
        return false;
    }

    public Long chargePoint(ChargePointDto chargePointDto) {
        Optional<Member> result = userRepository.findById(chargePointDto.getId());

        Member member = result.orElseThrow(() ->  new UserException(ErrorCode.USER_NOT_FOUND));
        Long newPoint = member.getPoint() + chargePointDto.getPointsToAdd();
        member.setPoint(newPoint);

        userRepository.save(member);

        return newPoint;
    }


    public UserDto getKakaoMember(String accessToken) {
        // accessToken을 이용해서 사용자 정보(닉네임) 가져오기
        String nickname = getNicknameFromKakaoAccessToken(accessToken);

        // 기존에 DB에 회원 정보가 있는 경우
        Member result = userRepository.findBynickName(nickname);
        if(result != null) {
            UserDto userDto = entityToDTO(result);

            log.info("exits,,,,,,,,,,," + userDto);
            return userDto;
        }

        Member socialMember = makeSocialMember(nickname);
        userRepository.save(socialMember);

        UserDto userDto = entityToDTO(socialMember);
        return userDto;
    }

    private Member makeSocialMember(String nickname) {
        String tempPassword = makeTempPassword();
        log.info("tempPassword: " + tempPassword);

        Member member = Member.builder()
                .name("kakao 회원입니다.")
                .email("kakao 회원입니다.")
                .password(passwordEncoder.encode(tempPassword))
                .nickName(nickname)
                .social(true)
                .build();

        return member;
    }
    private String getNicknameFromKakaoAccessToken(String accessToken) {
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "Content-type: application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();
        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("properties");
        String nickname = kakaoAccount.get("nickname");

        return nickname;
    }

    private String makeTempPassword() {
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < 8; i++) {
            buffer.append((char) ((int)(Math.random()*55) + 66));
        }
        return buffer.toString();
    }
}
