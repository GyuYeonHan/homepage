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
        if (role.equals("ROLE_USER")) {
            return Role.USER;
        } else if (role.equals("ROLE.STUDENT")) {
            return Role.STUDENT;
        } else if (role.equals("ROLE.TEACHER")) {
            return Role.TEACHER;
        } else if (role.equals("ROLE.ADMIN")) {
            return Role.ADMIN;
        } else {
            return null;
        }
    }
}
