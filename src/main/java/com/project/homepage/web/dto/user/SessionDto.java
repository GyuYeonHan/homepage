package com.project.homepage.web.dto.user;

import lombok.Data;

@Data
public class SessionDto {
    private boolean loggedIn;
    private String username;

    public SessionDto(boolean loggedIn, String username) {
        this.loggedIn = loggedIn;
        this.username = username;
    }
}
