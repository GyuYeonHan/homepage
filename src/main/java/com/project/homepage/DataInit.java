package com.project.homepage;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.CommentService;
import com.project.homepage.service.PostService;
import com.project.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DataInit {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    private Post post;
    private User user;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            userInit(Integer.toString(i));
            postInit(Integer.toString(i));
            commentInit(Integer.toString(i));
        }
    }

    private void userInit(String number) {
        user = User.builder()
                .username("관리자" + number)
                .role(Role.ADMIN)
                .loginId("admin" + number)
                .password("admin")
                .build();

        userService.save(user);
    }

    private void postInit(String number) {
        String title = "테스트 게시글" + number;
        String content = "테스트 본문" + number;

        post = Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();

        postService.save(post);
    }
    private void commentInit(String number) {
        Comment comments = Comment.builder()
                .post(post)
                .user(user)
                .content("테스트 댓글" + number)
                .build();

        commentService.save(comments);
    }
}
