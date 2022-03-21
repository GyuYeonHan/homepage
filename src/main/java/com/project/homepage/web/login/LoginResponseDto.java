package com.project.homepage.web.login;

import lombok.Data;

@Data
public class LoginResponseDto {

    private LoginStatus loginStatus;
    private String username;

    public LoginResponseDto(LoginStatus loginStatus, String username) {
        this.loginStatus = loginStatus;
        this.username = username;
    }
}
