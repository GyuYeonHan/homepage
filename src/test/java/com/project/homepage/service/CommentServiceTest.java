package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.post.PostType;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    NoticeRepository noticeRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글을 저장한다.")
    void save_comment() {
        //given
        Post post = Post.builder().type(PostType.ANNOUNCEMENT).build();
        User postOwner = User.builder().username("post").build();
        User commentOwner = User.builder().username("comment").build();
        post.setUser(postOwner);

        Comment comment = Comment.builder().build();
        comment.setUser(commentOwner);
        comment.setPost(post);

        given(commentRepository.save(comment)).willReturn(comment);

        //when
        commentService.save(comment);

        //then
        then(commentRepository).should(times(1)).save(comment);
    }

    @Test
    @DisplayName("댓글을 저장할 떄 글 작성자에게 알림이 생성된다.")
    void create_notice_to_post_owner_when_save_comment() {
        //given
        Post post = Post.builder().type(PostType.ANNOUNCEMENT).build();
        User postOwner = User.builder().username("post").build();
        User commentOwner = User.builder().username("comment").build();
        post.setUser(postOwner);

        Comment comment = Comment.builder().build();
        comment.setUser(commentOwner);
        comment.setPost(post);

        //when
        commentService.save(comment);

        //then
        then(noticeRepository).should(times(1)).save(any(Notice.class));
    }

    @Nested
    @DisplayName("댓글 ID로 조회하기")
    class find_comment_by_id {

        @Test
        @DisplayName("존재하는 ID 조회")
        public void exist_id() {
            //given
            Comment comment = Comment.builder().content("test").build();
            given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

            //when
            Comment findComment = commentService.findById(1L);

            //then
            then(commentRepository).should(times(1)).findById(1L);
            assertThat(findComment.getContent()).isEqualTo("test");
        }

        @Test
        @DisplayName("존재하지 않는 ID 조회")
        public void null_id() {
            //given
            given(commentRepository.findById(1L)).willReturn(Optional.empty());

            //when
            assertThatThrownBy(() -> commentService.findById(1L))
                    .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("해당 댓글이 없습니다.");

            //then
            then(commentRepository).should(times(1)).findById(1L);
        }

    }

    @Test
    @DisplayName("댓글을 삭제한다.")
    void delete_comment() {
        //given
        Comment comment = Comment.builder().build();

        //when
        commentService.delete(comment);

        //then
        then(commentRepository).should(times(1)).delete(comment);
    }
}