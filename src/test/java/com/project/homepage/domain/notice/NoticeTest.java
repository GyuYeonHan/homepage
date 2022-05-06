package com.project.homepage.domain.notice;

import com.project.homepage.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {

    @Test
    @DisplayName("메시지, URL, 유저를 통해 알림 생성")
    void create_notice() {
        //given
        User user = User.builder().build();
        String message = "test message";
        String url = "/test";

        //when
        Notice createdNotice = Notice.create(message, url, user);

        //then
        assertThat(createdNotice.getMessage()).isEqualTo(message);
        assertThat(createdNotice.getUrl()).isEqualTo(url);
        assertThat(createdNotice.getUser()).isEqualTo(user);
        assertThat(createdNotice.getStatus()).isEqualTo(NoticeStatus.UNREAD);
    }

    @Test
    @DisplayName("초기 알림 생성시 읽지 않음 상태이다.")
    void initial_notice_status_unread() {
        //given
        User user = User.builder().build();
        String message = "test message";
        String url = "/test";

        //when
        Notice createdNotice = Notice.create(message, url, user);

        //then
        assertThat(createdNotice.getStatus()).isEqualTo(NoticeStatus.UNREAD);
    }

    @Test
    @DisplayName("알림을 읽으면 상태가 읽음으로 바뀐다.")
    void read() {
        //given
        User user = User.builder().build();
        String message = "test message";
        String url = "/test";
        Notice notice = Notice.create(message, url, user);

        //when
        assertThat(notice.getStatus()).isEqualTo(NoticeStatus.UNREAD);
        notice.read();

        //then
        assertThat(notice.getStatus()).isEqualTo(NoticeStatus.READ);
    }
}