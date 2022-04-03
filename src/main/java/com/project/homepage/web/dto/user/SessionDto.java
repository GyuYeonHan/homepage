package com.project.homepage.web.dto.user;

import lombok.Data;

@Data
public class SessionDto {
    private boolean loggedIn;
    private Long userId;
    private String username;

    public SessionDto(boolean loggedIn, Long userId, String username) {
        this.loggedIn = loggedIn;
        this.userId = userId;
        this.username = username;
    }
}
