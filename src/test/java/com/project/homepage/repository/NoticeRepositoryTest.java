package com.project.homepage.repository;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NoticeRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired NoticeRepository noticeRepository;

    private User user;

    @BeforeEach
    void setUp() {
        noticeRepository.deleteAll();
        userRepository.deleteAll();

        user = User.builder()
                .username("userA")
                .role(Role.ADMIN)
                .loginId("userA")
                .password("userA")
                .build();

        userRepository.save(user);

        for (int i = 1; i < 11; i++) {
            Notice notice = Notice.builder()
                    .status(NoticeStatus.UNREAD)
                    .message("안 읽은 메시지" + i)
                    .url("unread url" + i)
                    .user(user)
                    .build();

            noticeRepository.save(notice);
        }

        for (int i = 1; i < 11; i++) {
            Notice notice = Notice.builder()
                    .status(NoticeStatus.READ)
                    .message("읽은 메시지" + i)
                    .url("read url" + i)
                    .user(user)
                    .build();

            noticeRepository.save(notice);
        }
    }

    @Test
    @DisplayName("유저를 null로 조회 시 사이즈 0의 리스트가 반환된다.")
    void findByNull() {
        Optional<List<Notice>> allNotice = noticeRepository.findAllByUser(null);

        assertTrue(allNotice.isPresent());
        assertThat(allNotice.get().size()).isEqualTo(0);
    }

    @Test
    void findAllByUser() {
        Optional<List<Notice>> allNotice = noticeRepository.findAllByUser(user);

        assertTrue(allNotice.isPresent());
        assertThat(allNotice.get().size()).isEqualTo(20);
    }

    @Test
    void findAllUnreadByUser() {
        Optional<List<Notice>> allNotice = noticeRepository.findUnreadByUser(user);

        assertTrue(allNotice.isPresent());
        assertThat(allNotice.get().size()).isEqualTo(10);
        assertThat(allNotice.get().get(0).getStatus()).isEqualTo(NoticeStatus.UNREAD);
    }
}