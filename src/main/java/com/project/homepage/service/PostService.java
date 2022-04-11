package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.post.PostType;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.PostRepository;
import com.project.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final NoticeService noticeService;
    private final UserRepository userRepository;

    @Transactional
    public void saveAnnouncement(Post post) {
        post.setType(PostType.ANNOUNCEMENT);
        Post savedPost = postRepository.save(post);

        List<User> allAdmin = userRepository.findAllAdmin();
        for (User admin : allAdmin) {
            String noticeMessage = "새 공지사항[" + savedPost.getTitle() + "]이 작성되었습니다.";
            String noticeUrl = "/announcement/" + savedPost.getId();
            noticeService.create(noticeMessage, noticeUrl, admin);
        }

    }

    @Transactional
    public void saveQuestion(Post post) {
        post.setType(PostType.QUESTION);
        Post savedPost = postRepository.save(post);

        List<User> allAdmin = userRepository.findAllAdmin();
        for (User admin : allAdmin) {
            String noticeMessage = "새 질문[" + savedPost.getTitle() + "]이 작성되었습니다.";
            String noticeUrl = "/question/" + savedPost.getId();
            noticeService.create(noticeMessage, noticeUrl, admin);
        }

    }

    @Transactional
    public void edit(Long id, String title, String content) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        post.update(title, content);
    }

    @Transactional
    public void delete(Post post) {
        List<Comment> commentList = post.getCommentList();

        for (Comment comment : commentList) {
            commentRepository.delete(comment);
        }
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
}
