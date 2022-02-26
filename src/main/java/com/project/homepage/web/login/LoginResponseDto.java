package com.project.homepage.web.login;

import com.project.homepage.domain.user.User;
import lombok.Data;

@Data
public class LoginResponseDto {

    private User user;
    private String sessionId;
    private String redirectURL;

    public LoginResponseDto(User user, String sessionId, String redirectURL) {
        this.user = user;
        this.sessionId = sessionId;
        this.redirectURL = redirectURL;
    }
}
