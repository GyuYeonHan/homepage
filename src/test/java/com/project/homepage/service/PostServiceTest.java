package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.PostRepository;
import com.project.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    User userA;
    String userA_username = "유저A";
    String userA_loginID = "userAID";
    String userA_password = "userAPassword";

    Comment comment1;
    String comment1_content = "댓글 1 본문";

    Post post1;
    String post1_title = "게시글 1";
    String post1_content = "게시글 1 본문";

    public void init() {
        userInit();
        postInit();
        commentInit();
    }

    public void clear() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void userInit() {
        userA = User.builder()
                .username(userA_username)
                .role(Role.ADMIN)
                .loginId(userA_loginID)
                .password(userA_password)
                .build();

        userRepository.save(userA);
    }
    private void postInit() {
        post1 = Post.builder()
                .title(post1_title)
                .content(post1_content)
                .user(userA)
                .build();
        postService.save(post1);
    }
    private void commentInit() {
        comment1 = Comment.builder()
                .post(post1)
                .user(userA)
                .content(comment1_content)
                .build();

        commentRepository.save(comment1);
    }

    @BeforeEach
    void setUp() {
        init();
    }

    @AfterEach
    void tearDown() {
        clear();
    }

    @Test
    @DisplayName("저장된 게시글을 조회하기")
    void save() {
        List<Post> postList = postRepository.findAll();
        Post saved_post1 = postList.get(0);
        assertThat(saved_post1.getTitle()).isEqualTo(post1_title);
        assertThat(saved_post1.getContent()).isEqualTo(post1_content);
    }

    @Test
    @DisplayName("저장된 게시글을 삭제하기")
    void delete() {
        //given
        postService.delete(post1);

        //then
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList.size()).isEqualTo(0);
        List<Post> postList = postRepository.findAll();
        assertThat(postList.size()).isEqualTo(0);
    }
}