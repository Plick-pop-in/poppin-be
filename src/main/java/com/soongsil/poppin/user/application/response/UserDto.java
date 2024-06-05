package com.soongsil.poppin.user.application.response;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public class UserDto extends User {
    Long id, point;
    private String name, email, password, nickname, role;
    boolean social;

    public UserDto(Long id, String name, String email, String password, String nickname, boolean social, Long point) {
        super(
                email,
                password,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = "ROLE_USER";
        this.social = social;
        this.point = point;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", id);
        dataMap.put("name", name);
        dataMap.put("email", email);
        dataMap.put("password", password);
        dataMap.put("nickname", nickname);
        dataMap.put("role", role);
        dataMap.put("social", social);
        dataMap.put("point", point);

        return dataMap;
    }
}

