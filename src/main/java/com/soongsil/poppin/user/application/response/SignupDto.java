package com.soongsil.poppin.user.application.response;

import lombok.Data;

@Data
public class SignupDto {
    private String name, email, password, confirmPassword, nickname;
}
