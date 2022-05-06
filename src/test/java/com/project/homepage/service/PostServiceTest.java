package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.post.PostType;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.NoticeRepository;
import com.project.homepage.repository.PostRepository;
import com.project.homepage.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    NoticeRepository noticeRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글을 저장한다.")
    void save_post() {
        //given
        Post post = Post.builder().type(PostType.ANNOUNCEMENT).build();
        given(postRepository.save(post)).willReturn(post);

        //when
        postService.savePost(post);

        //then
        then(postRepository).should(times(1)).save(post);
    }

    @Test
    @DisplayName("게시글을 저장할 때 모든 admin에게 알림이 생성된다.")
    void create_notice_when_save_post() {
        //given
        Post post = Post.builder().type(PostType.ANNOUNCEMENT).build();
        List<User> allAdminList = new ArrayList<>();
        allAdminList.add(User.builder().role(Role.ADMIN).build());
        allAdminList.add(User.builder().role(Role.ADMIN).build());
        given(userRepository.findAllAdmin()).willReturn(allAdminList);
        given(postRepository.save(post)).willReturn(post);

        //when
        postService.savePost(post);

        //then
        then(noticeRepository).should(times(allAdminList.size())).save(any(Notice.class));
    }

    @Test
    @DisplayName("게시글 리스트 전체 조회하기")
    void view_all_post() {
        //given
        List<Post> MockPostList = new ArrayList<>();
        MockPostList.add(Post.builder().title("test1").build());
        MockPostList.add(Post.builder().title("test2").build());
        given(postRepository.findAllPostDesc()).willReturn(MockPostList);

        //when
        List<Post> postList = postService.findAllPost();

        //then
        then(postRepository).should(times(1)).findAllPostDesc();
        assertThat(postList.size()).isEqualTo(2);
        assertThat(postList.get(0).getTitle()).isEqualTo("test1");
        assertThat(postList.get(1).getTitle()).isEqualTo("test2");
    }

    @Test
    @DisplayName("게시글 리스트 중 공지사항 전체 조회하기")
    void view_all_announcement() {
        //given
        List<Post> MockPostList = new ArrayList<>();
        MockPostList.add(Post.builder().title("announcement1").type(PostType.ANNOUNCEMENT).build());
        MockPostList.add(Post.builder().title("announcement2").type(PostType.ANNOUNCEMENT).build());
        given(postRepository.findAllAnnouncementPostDesc()).willReturn(MockPostList);

        //when
        List<Post> announcementList = postService.findAllAnnouncementPost();

        //then
        then(postRepository).should(times(1)).findAllAnnouncementPostDesc();
        assertThat(announcementList.size()).isEqualTo(2);
        assertThat(announcementList.get(0).getTitle()).isEqualTo("announcement1");
        assertThat(announcementList.get(0).getType()).isEqualTo(PostType.ANNOUNCEMENT);
        assertThat(announcementList.get(1).getTitle()).isEqualTo("announcement2");
        assertThat(announcementList.get(1).getType()).isEqualTo(PostType.ANNOUNCEMENT);
    }

    @Test
    @DisplayName("게시글 리스트 중 질문 전체 조회하기")
    void view_all_question() {
        //given
        List<Post> MockPostList = new ArrayList<>();
        MockPostList.add(Post.builder().title("question1").type(PostType.QUESTION).build());
        MockPostList.add(Post.builder().title("question2").type(PostType.QUESTION).build());
        given(postRepository.findAllQuestionPostDesc()).willReturn(MockPostList);

        //when
        List<Post> questionList = postService.findAllQuestionPost();

        //then
        then(postRepository).should(times(1)).findAllQuestionPostDesc();
        assertThat(questionList.size()).isEqualTo(2);
        assertThat(questionList.get(0).getTitle()).isEqualTo("question1");
        assertThat(questionList.get(0).getType()).isEqualTo(PostType.QUESTION);
        assertThat(questionList.get(1).getTitle()).isEqualTo("question2");
        assertThat(questionList.get(1).getType()).isEqualTo(PostType.QUESTION);
    }

    @Nested
    @DisplayName("게시글 ID로 조회하기")
    class view_one_post_by_id {

        @Test
        @DisplayName("존재하는 ID 조회")
        public void exist_id() {
            //given
            Post post = Post.builder().title("test").build();
            given(postRepository.findById(1L)).willReturn(Optional.of(post));

            //when
            Post findPost = postService.findById(1L);

            //then
            then(postRepository).should(times(1)).findById(1L);
            assertThat(findPost.getTitle()).isEqualTo("test");
        }

        @Test
        @DisplayName("존재하지 않는 ID 조회")
        public void null_id() {
            //given
            given(postRepository.findById(1L)).willReturn(Optional.empty());

            //when
            assertThatThrownBy(() -> postService.findById(1L))
                    .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("해당 게시글이 없습니다.");

            //then
            then(postRepository).should(times(1)).findById(1L);
        }
    }

    @Nested
    @DisplayName("게시글 제목과 내용 수정하기")
    class update_post_title_and_content {

        @Test
        @DisplayName("존재하는 게시글 ID 수정")
        public void exist_id() {
            //when
            Post post = Post.builder().title("before title").content("before content").build();
            given(postRepository.findById(1L)).willReturn(Optional.of(post));

            //given
            String afterTitle = "after title";
            String afterContent = "after content";
            Post editedPost = postService.edit(1L, afterTitle, afterContent);

            //then
            then(postRepository).should(times(1)).findById(1L);
            assertThat(editedPost.getTitle()).isEqualTo(afterTitle);
            assertThat(editedPost.getContent()).isEqualTo(afterContent);
        }

        @Test
        @DisplayName("존재하지 않는 게시글 ID 수정")
        public void null_id() {
            //when
            given(postRepository.findById(1L)).willReturn(Optional.empty());

            //given
            String afterTitle = "after title";
            String afterContent = "after content";
            assertThatThrownBy(() -> postService.edit(1L, afterTitle, afterContent))
                    .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("해당 게시글이 없습니다.");

            //then
            then(postRepository).should(times(1)).findById(1L);
        }
    }

    @Test
    @DisplayName("저장된 게시글을 삭제하면 글과 댓글이 모두 삭제된다.")
    void delete_post() {
        //given
        Post post = Post.builder().build();
        Comment comment1 = Comment.builder().build();
        Comment comment2 = Comment.builder().build();
        comment1.setPost(post);
        comment2.setPost(post);

        //when
        postService.delete(post);

        //then
        then(postRepository).should(times(1)).delete(post);
        then(commentRepository).should(times(1)).delete(comment1);
        then(commentRepository).should(times(1)).delete(comment2);
    }
}