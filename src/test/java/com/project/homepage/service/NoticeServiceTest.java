package com.project.homepage.service;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

    @Mock
    NoticeRepository noticeRepository;

    @InjectMocks
    NoticeService noticeService;

    @Nested
    @DisplayName("알림 ID로 조회하기")
    class find_notice_by_id {

        @Test
        @DisplayName("존재하는 ID 조회")
        public void exist_id() {
            //given
            Notice notice = Notice.create("test", null, null);
            given(noticeRepository.findById(1L)).willReturn(Optional.of(notice));

            //when
            Notice findNotice = noticeService.findById(1L);

            //then
            then(noticeRepository).should(times(1)).findById(1L);
            assertThat(findNotice.getMessage()).isEqualTo("test");
        }

        @Test
        @DisplayName("존재하지 않는 ID 조회")
        public void null_id() {
            //given
            given(noticeRepository.findById(1L)).willReturn(Optional.empty());

            //when
            assertThatThrownBy(() -> noticeService.findById(1L))
                    .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("해당 알림이 없습니다.");

            //then
            then(noticeRepository).should(times(1)).findById(1L);
        }
    }

    @Test
    @DisplayName("해당 유저의 모든 알림을 조회한다.")
    void find_all_notice_by_user() {
        //given
        User user = User.builder().build();

        //when
        List<Notice> allNotices = noticeService.findAllNoticeByUser(user);

        //then
        then(noticeRepository).should(times(1)).findAllByUser(user);
    }

    @Test
    @DisplayName("해당 유저의 읽지 않은 알림을 조회한다.")
    void find_all_unread_notice_by_user() {
        //given
        User user = User.builder().build();

        //when
        noticeService.findUnreadNoticeByUser(user);

        //then
        then(noticeRepository).should(times(1)).findUnreadByUser(user);
    }

}