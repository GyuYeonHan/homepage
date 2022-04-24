package com.project.homepage.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.web.dto.user.UserSaveRequestDto;
import com.project.homepage.web.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("유저 API 테스트")
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession session;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
        if (session != null) {
            session.clearAttributes();
        }
    }

    @Test
    @DisplayName("유저 1명 조회 시 200으로 성공한다.")
    void get_one_user_success() throws Exception {
        //Given
        String testURL = "/api/user/1";

        //When
        ResultActions resultActions = mockMvc.perform(
                get(testURL).accept(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("username").value("test1"))
                .andExpect(jsonPath("loginId").value("test1"))
                .andExpect(jsonPath("role").value("관리자"));
    }

    @Test
    @DisplayName("없는 유저를 조회하면 404를 반환한다.")
    void get_null_user() throws Exception {
        //Given
        String testURL = "/api/user/100";

        //When
        ResultActions resultActions = mockMvc.perform(
                get(testURL));

        //Then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("유저 조회 시 아이디에 잘못된 값을 입력하면 400을 반환한다.")
    void get_one_user_with_wrong_parameter() throws Exception {
        //Given
        String testURL = "/api/user/abc";

        //When
        ResultActions resultActions = mockMvc.perform(
                get(testURL));

        //Then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("어드민 권한으로 유저 추가시 201로 성공한다.")
    void save_user() throws Exception {
        //Given
        String testURL = "/api/user";
        UserSaveRequestDto saveUser = new UserSaveRequestDto();
        saveUser.setRole("ROLE_USER");
        saveUser.setUsername("Han");
        saveUser.setLoginId("testuser");
        saveUser.setPassword("testuser");

        User admin = User.builder()
                .role(Role.ADMIN)
                .build();

        session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_USER, admin);

        String contentBody = objectMapper.writeValueAsString(saveUser);

        //When
        ResultActions resultActions = mockMvc.perform(
                post(testURL)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody));

        //Then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("세션이 없을 시 유저를 추가하면 401을 반환한다.")
    void save_user_no_session() throws Exception {
        //Given
        String testURL = "/api/user";
        UserSaveRequestDto saveUser = new UserSaveRequestDto();
        saveUser.setRole("ROLE_USER");
        saveUser.setUsername("Han");
        saveUser.setLoginId("testuser");
        saveUser.setPassword("testuser");

        String contentBody = objectMapper.writeValueAsString(saveUser);

        //When
        ResultActions resultActions = mockMvc.perform(
                post(testURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody));

        //Then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("세션은 있지만 어드민 권한이 아닐 때 유저 추가시 401로 실패한다.")
    void save_user_no_auth() throws Exception {
        //Given
        String testURL = "/api/user";
        UserSaveRequestDto saveUser = new UserSaveRequestDto();
        saveUser.setRole("ROLE_USER");
        saveUser.setUsername("Han");
        saveUser.setLoginId("testuser");
        saveUser.setPassword("testuser");

        User user = User.builder()
                .role(Role.USER)
                .build();

        session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);

        String contentBody = objectMapper.writeValueAsString(saveUser);

        //When
        ResultActions resultActions = mockMvc.perform(
                post(testURL)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody));

        //Then
        resultActions.andExpect(status().isUnauthorized());
    }

}