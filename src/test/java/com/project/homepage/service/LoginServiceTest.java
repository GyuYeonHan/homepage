package com.project.homepage.service;

import com.project.homepage.domain.user.User;
import com.project.homepage.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    LoginService loginService;

    @Nested
    @DisplayName("아이디와 패스워드로 로그인한다.")
    class login {

        @Test
        @DisplayName("로그인에 성공하면 유저를 반환한다.")
        public void success() {
            //given
            String loginId = "testId";
            String password = "testPw";
            User user = User.builder().loginId(loginId).password(password).build();
            given(userRepository.findByLoginId(loginId)).willReturn(Optional.of(user));

            //when
            User loginUser = loginService.login(loginId, password);

            //then
            then(userRepository).should(times(1)).findByLoginId(loginId);
            assertThat(loginUser.getLoginId()).isEqualTo(loginId);
            assertThat(loginUser.getPassword()).isEqualTo(password);
        }

        @Test
        @DisplayName("아이디가 존재하지 않으면 로그인에 실패한다.")
        public void login_fail_with_null_login_id() {
            //given
            String loginId = "testId";
            String password = "testPw";
            given(userRepository.findByLoginId(loginId)).willReturn(Optional.empty());

            //when
            User loginUser = loginService.login(loginId, password);

            //then
            then(userRepository).should(times(1)).findByLoginId(loginId);
            assertThat(loginUser).isNull();
        }

        @Test
        @DisplayName("아이디는 존재하지만 패스워드가 틀리면 로그인에 실패한다.")
        public void login_fail_with_wrong_password() {
            //given
            String loginId = "testId";
            String password = "testPw";
            User user = User.builder().loginId(loginId).password("wrongPw").build();
            given(userRepository.findByLoginId(loginId)).willReturn(Optional.of(user));

            //when
            User loginUser = loginService.login(loginId, password);

            //then
            then(userRepository).should(times(1)).findByLoginId(loginId);
            assertThat(loginUser).isNull();
        }
    }

    @Test
    @DisplayName("해당 아이디의 유저가 존재하는지 체크한다.")
    void userExist() {
        //given
        String loginId = "testId";
        User user = User.builder().loginId(loginId).build();
        given(userRepository.findByLoginId(loginId)).willReturn(Optional.of(user));

        //when
        boolean exist = loginService.userExist(loginId);

        //then
        then(userRepository).should(times(1)).findByLoginId(loginId);
        assertThat(exist).isTrue();
    }
}