package com.project.homepage.web.login;

import lombok.Data;

@Data
public class LoginResponseDto {

    private LoginStatus loginStatus;
    private Long userId;
    private String username;

    public LoginResponseDto(LoginStatus loginStatus, Long userId, String username) {
        this.loginStatus = loginStatus;
        this.userId = userId;
        this.username = username;
    }
}
