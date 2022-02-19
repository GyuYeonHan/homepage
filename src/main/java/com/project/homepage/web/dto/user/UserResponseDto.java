package com.project.homepage.web.dto.user;

import com.project.homepage.domain.user.User;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String role;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.role = entity.getRole().getRole();
    }
}
