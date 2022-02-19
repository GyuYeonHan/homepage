package com.project.homepage.web.dto.user;

import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserSaveRequestDto {

    @NotNull
    private Role role;

    @NotBlank
    private String username;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public User toEntity() {
        return User.builder()
                .role(role)
                .username(username)
                .loginId(loginId)
                .password(password)
                .build();
    }
}
