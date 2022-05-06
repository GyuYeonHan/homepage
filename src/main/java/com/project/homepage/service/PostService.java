package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.NoticeRepository;
import com.project.homepage.repository.PostRepository;
import com.project.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public Post savePost(Post post) {
        Post savedPost = postRepository.save(post);

        // 모든 admin에게 글 생성 알림을 생성함.
        List<User> allAdmin = userRepository.findAllAdmin();
        for (User admin : allAdmin) {
            createNotice(post, admin);
        }

        return savedPost;
    }

    @Transactional
    public Post edit(Long id, String title, String content) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        post.update(title, content);

        return post;
    }

    @Transactional
    public void delete(Post post) {
        // 글에 달린 모든 댓글을 삭제
        List<Comment> commentList = post.getCommentList();

        for (Comment comment : commentList) {
            commentRepository.delete(comment);
        }

        // 댓글 삭제 후 글 삭제
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPost() {
        return postRepository.findAllPostDesc();
    }

    @Transactional(readOnly = true)
    public List<Post> findAllAnnouncementPost() {
        return postRepository.findAllAnnouncementPostDesc();
    }

    @Transactional(readOnly = true)
    public List<Post> findAllQuestionPost() {
        return postRepository.findAllQuestionPostDesc();
    }

//    @Transactional(readOnly = true)
//    public Page<Post> findAllPost() {
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        return postRepository.findAll(pageRequest);
//    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    }

    private Notice createNotice(Post post, User admin) {
        String message = "새 " + post.getType().getType() + "[" + post.getTitle() + "]이 작성되었습니다.";
        String url = post.getType().getUrl() + "/" + post.getId();

        Notice notice = Notice.create(message, url, admin);

        noticeRepository.save(notice);

        return notice;
    }
}
