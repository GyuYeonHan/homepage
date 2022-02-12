package com.project.homepage;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.PostRepository;
import com.project.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DataInit {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private Post post;
    private User user;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        userInit();
        postInit();
        commentInit();
    }

    private void userInit() {
        user = User.builder()
                .username("유저A")
                .role(Role.ADMIN)
                .loginId("test")
                .password("test")
                .build();

        userRepository.save(user);
    }

    private void postInit() {
        String title = "테스트 게시글";
        String content = "테스트 본문";

        post = Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();

        postRepository.save(post);
    }
    private void commentInit() {
        Comment comments1 = Comment.builder()
                .post(post)
                .user(user)
                .content("테스트 댓글1")
                .build();

        commentRepository.save(comments1);
    }
}
