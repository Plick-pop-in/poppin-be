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

@RequiredArgsConstructor
@Service
@Log4j2
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    // UserDetails -> UserDto, username -> email 이라고 생각하면 된다.
    // post */user/login 하면 username에 email값 들어옴.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("------loadUserByEmail-----" + username);

        // Member is Entity
        Member member = userRepository.getWithEmail(username);

        if(member == null) {
            throw new UsernameNotFoundException("Not Found");
        }

        UserDto userDto = new UserDto(
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getNickName()
        );

        log.info(userDto);

        return userDto;
    }
}
