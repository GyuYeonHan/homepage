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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("유저를 저장한다.")
    void save_user() {
        //given
        User user = User.builder().build();

        //when
        userService.save(user);

        //then
        then(userRepository).should(times(1)).save(user);
    }

    @Test
    @DisplayName("모든 유저를 조회한다.")
    void find_all_user() {
        //given
        User user = User.builder().build();

        //when
        userService.findAllUser();

        //then
        then(userRepository).should(times(1)).findAllDesc();
    }

    @Nested
    @DisplayName("유저 ID로 조회하기")
    class view_one_post_by_id {

        @Test
        @DisplayName("존재하는 ID 조회")
        public void exist_id() {
            //given
            User user = User.builder().build();
            given(userRepository.findById(1L)).willReturn(Optional.of(user));

            //when
            userService.findById(1L);

            //then
            then(userRepository).should(times(1)).findById(1L);
        }

        @Test
        @DisplayName("존재하지 않는 ID 조회")
        public void null_id() {
            //given
            given(userRepository.findById(1L)).willReturn(Optional.empty());

            //when
            assertThatThrownBy(() -> userService.findById(1L))
                    .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("해당 유저가 없습니다.");

            //then
            then(userRepository).should(times(1)).findById(1L);
        }
    }

    @Test
    @DisplayName("유저를 삭제한다.")
    void delete_user() {
        //given
        User user = User.builder().build();

        //when
        userService.delete(user);

        //then
        then(userRepository).should(times(1)).delete(user);
    }
}