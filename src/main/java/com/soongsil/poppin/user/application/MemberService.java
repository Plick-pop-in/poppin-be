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
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


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

    public void modifyPassword(String userId) {
        Long userIdAsLong = Long.parseLong(userId);

        CompletableFuture.runAsync(() -> {
            try{
                sendEmailForCertification(userIdAsLong);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setPassword(String email) {
        email = email.replace("%40", "@");
        Member member = userRepository.getWithEmail(email + "om");

        log.info("아이디!!!" + member);
        if (member == null) {
            throw new UserException(ErrorCode.USER_NOT_FOUND);
        }
        CompletableFuture.runAsync(() -> {
            try{
                sendEmailForCertification(member.getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

    //이메일 보내기
    public void sendEmailForCertification(Long userId) throws MessagingException {

        // 비밀번호 생성
        String certificationNumber = makeTempPassword();

        String img = "<img src='http://www.plcik.shop/plick_logo.png' alt='Plick Logo'>";
        String link = "<a href='http://d2vr7xh1eokzzb.cloudfront.net/Login'>로그인 링크</a>";

        String content = String.format("%s <br> 임시비밀번호: %s <br><br> %s <br> 로그인 후 마이페이지에서 비밀번호를 수정해주세요.",
                img,
                certificationNumber,
                link);

        // 비밀번호 인코딩
        String userPw = passwordEncoder.encode(certificationNumber);

        // DB에 비밀번호 저장
        Optional<Member> result = userRepository.findById(userId);
        Member member = result.orElseThrow(() ->  new UserException(ErrorCode.USER_NOT_FOUND));
        member.setPassword(userPw);
        userRepository.save(member);

        // 이메일 전송
        sendMail(member.getEmail(), content);
    }


    private final JavaMailSender mailSender;
    private void sendMail(String email, String content) throws MessagingException {

        // 이메일 객체 생성
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        // 수신자, 제목, 내용 설정
        helper.setTo(email);
        helper.setSubject("Pop`in 비밀번호 변경 메일");
        helper.setText(content, true); // html변환 전달

        // 메일 전송
        mailSender.send(mimeMessage);
    }
}
