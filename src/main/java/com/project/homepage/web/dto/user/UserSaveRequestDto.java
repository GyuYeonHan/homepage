package com.project.homepage.web.dto.user;

import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserSaveRequestDto {

    @NotNull
    private String role;

    @NotBlank
    private String username;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public User toEntity() {
        return User.builder()
                .role(createRole(role))
                .username(username)
                .loginId(loginId)
                .password(password)
                .build();
    }

    private Role createRole(String role) {
        switch (role) {
            case "ROLE_USER":
                return Role.USER;
            case "ROLE_STUDENT":
                return Role.STUDENT;
            case "ROLE_TEACHER":
                return Role.TEACHER;
            case "ROLE_ADMIN":
                return Role.ADMIN;
            default:
                return null;
        }
    }
}
