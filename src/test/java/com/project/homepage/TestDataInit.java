package com.project.homepage;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.post.PostType;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.CommentService;
import com.project.homepage.service.PostService;
import com.project.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("test")
@Component
@RequiredArgsConstructor
public class TestDataInit {

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
        for (int i = 1; i < 4; i++) {
            userInit(Integer.toString(i));
            postInit(Integer.toString(i));
            commentInit(Integer.toString(i));
        }
    }

    private void userInit(String number) {
        user = User.builder()
                .username("test" + number)
                .role(Role.ADMIN)
                .loginId("test" + number)
                .password("test")
                .build();

        userService.save(user);
    }

    private void postInit(String number) {
        String title = "title" + number;
        String content = "test post content";

        post = Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();

        if (Integer.parseInt(number) % 2 == 0) {
            post.setType(PostType.ANNOUNCEMENT);
        } else {
            post.setType(PostType.QUESTION);
        }

        postService.savePost(post);
    }
    private void commentInit(String number) {
        Comment comments = Comment.builder()
                .post(post)
                .user(user)
                .content("test comment" + number)
                .build();

        commentService.save(comments);
    }
}
