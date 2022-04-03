package com.project.homepage.web.dto.user;

import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSaveRequestDtoTest {

    @Test
    @DisplayName("일반 사용자 역할이 생성된다.")
    void createRoleUser() {
        UserSaveRequestDto dto = new UserSaveRequestDto();
        dto.setRole("ROLE_USER");
        User user = dto.toEntity();

        assertEquals(Role.USER, user.getRole());
    }

    @Test
    @DisplayName("학부모 역할이 생성된다.")
    void createRolePARENT() {
        UserSaveRequestDto dto = new UserSaveRequestDto();
        dto.setRole("ROLE_PARENT");
        User user = dto.toEntity();

        assertEquals(Role.PARENT, user.getRole());
    }

    @Test
    @DisplayName("관리자 역할이 생성된다.")
    void createRoleADMIN() {
        UserSaveRequestDto dto = new UserSaveRequestDto();
        dto.setRole("ROLE_ADMIN");
        User user = dto.toEntity();

        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("학생 역할이 생성된다.")
    void createRoleSTUDENT() {
        UserSaveRequestDto dto = new UserSaveRequestDto();
        dto.setRole("ROLE_STUDENT");
        User user = dto.toEntity();

        assertEquals(Role.STUDENT, user.getRole());
    }


}