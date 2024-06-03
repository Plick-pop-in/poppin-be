package com.soongsil.poppin.user.application;

import com.soongsil.poppin.user.application.response.UserDto;
import com.soongsil.poppin.user.domain.Member;
import com.soongsil.poppin.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
//@Service
//@Log4j2
//public class SecurityService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("------loadUserByEmail-----" + username);
//        log.info("user : " + username);
//
//        Member member = userRepository.getWithEmail(username);
//
//        log.info("pw : " + member.getPassword());
//
//        if (member == null) {
//            throw new UsernameNotFoundException("Not Found");
//        }
//
//        UserDto userDto = new UserDto(
//                member.getName(),
//                member.getEmail(),
//                member.getPassword(),
//                member.getNickName()
//        );
//
//        log.info(userDto);
//
//        return userDto;
//    }
//}

//@RequiredArgsConstructor
//@Service
//@Log4j2
//public class SecurityService implements UserDetailsService {
//
//    private final UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("------loadUserByEmail-----" + username);
//        log.info("user : " + username);
//
//        Member member = userRepository.getWithEmail(username);
//        if (member == null) {
//            throw new UsernameNotFoundException("Not Found");
//        }
//
//        UserDto userDto = new UserDto(
//                member.getName(),
//                member.getEmail(),
//                member.getPassword(),
//                member.getNickName()
//        );

import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
@Log4j2
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("------loadUserByEmail-----" + username);
        log.info("user : " + username);

        Member member = userRepository.getWithEmail(username);

        log.info("Stored password: " + member.getPassword());

        if (member == null) {
            throw new UsernameNotFoundException("Not Found");
        }

        UserDto userDto = new UserDto(
                member.getUserId(),
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getNickName(),
                member.isSocial()
        );

        log.info(userDto);

        return userDto;
    }
}
