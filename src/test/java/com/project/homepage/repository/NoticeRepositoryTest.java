package com.project.homepage.repository;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import com.project.homepage.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class NoticeRepositoryTest {

    @InjectMocks
    NoticeRepository noticeRepository;

    @Test
    @DisplayName("유저를 null로 조회 시 사이즈 0의 리스트가 반환된다.")
    void findByNull() {
        //given
        User user = User.builder().build();
        List<Notice> allNotice = noticeRepository.findAllByUser(null);

        //when

        //then
        assertThat(allNotice.size()).isEqualTo(0);
    }

    @Test
    void findAllByUser() {
        User user = User.builder().build();
        List<Notice> allNotice = noticeRepository.findAllByUser(user);

        assertThat(allNotice.size()).isEqualTo(20);
    }

    @Test
    void findAllUnreadByUser() {
        User user = User.builder().build();
        List<Notice> allNotice = noticeRepository.findUnreadByUser(user);

        assertThat(allNotice.size()).isEqualTo(10);
        assertThat(allNotice.get(0).getStatus()).isEqualTo(NoticeStatus.UNREAD);
    }
}