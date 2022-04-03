package com.project.homepage.service;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class NoticeServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired NoticeService noticeService;

    @Test
    @DisplayName("글을 작성할 떄 알림이 생성된다.")
    void save() {
        //given
        User userA = User.builder()
                .username("userA")
                .role(Role.ADMIN)
                .loginId("userA")
                .password("userA")
                .build();

        userService.save(userA);

        Post post = Post.builder()
                .title("post title")
                .content("post content")
                .user(userA)
                .build();

        //when
        postService.save(post); // 알림 생성
        List<Notice> noticeList = noticeService.findAllNoticeByUser(userA);

        //then
        assertThat(noticeList.size()).isEqualTo(1);
        assertThat(noticeList.get(0).getStatus()).isEqualTo(NoticeStatus.UNREAD);
    }
}