package com.project.homepage.web.dto.user;

import com.project.homepage.domain.user.User;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String role;
    private String createdDate;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole().getRole();
        this.createdDate = user.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));;
    }
}
