package com.soongsil.poppin.user.application.response;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserDto extends User {
    private String name, email, password, nickname;

    public UserDto(String name, String email, String password, String nickname) {
        super(
                email,
                password,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("name", name);
        dataMap.put("email", email);
        dataMap.put("password", password);
        dataMap.put("nickname", nickname);

        return dataMap;
    }
}

