package com.project.homepage.service;

import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    User userA;
    String userA_username = "유저A";
    String userA_loginID = "userAID";
    String userA_password = "userAPassword";

    private void userInit() {
        userA = User.builder()
                .username(userA_username)
                .role(Role.ADMIN)
                .loginId(userA_loginID)
                .password(userA_password)
                .build();

        userRepository.save(userA);
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("올바른 아이디와 패스워드로 로그인에 성공합니다.")
    void loginSuccess() {
        //given
        userInit();

        //when
        User loginUser = loginService.login(userA_loginID, userA_password);

        //then
        Assertions.assertThat(loginUser.getLoginId()).isEqualTo(userA.getLoginId());
    }

    @Test
    @DisplayName("올바른 아이디와 잘못된 패스워드로 로그인에 실패합니다.")
    void loginFail1() {
        //given
        userInit();

        //when
        User loginUser = loginService.login(userA_loginID, "wrong");

        //then
        Assertions.assertThat(loginUser).isNull();
    }

    @Test
    @DisplayName("잘못된 아이디와 올바른 패스워드로 로그인에 실패합니다.")
    void loginFail2() {
        //given
        userInit();

        //when
        User loginUser = loginService.login("wrong", userA_password);

        //then
        Assertions.assertThat(loginUser).isNull();
    }

    @Test
    @DisplayName("잘못된 아이디와 패스워드로 로그인에 실패합니다.")
    void loginFail3() {
        //given
        userInit();

        //when
        User loginUser = loginService.login("wrong", "wrong");

        //then
        Assertions.assertThat(loginUser).isNull();
    }
}