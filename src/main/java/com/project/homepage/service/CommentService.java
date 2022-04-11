package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.post.PostType;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NoticeService noticeService;

    @Transactional
    public Comment save(Comment comment) {
        Post post = comment.getPost();
        User commentOwner = comment.getUser();
        User postOwner = comment.getPost().getUser();

        String noticeMessage = "글[" + post.getTitle() + "]에 " + commentOwner.getUsername() + "님의 댓글이 달렸습니다.";
        String noticeUrl;
        if (post.getType() == PostType.ANNOUNCEMENT) {
            noticeUrl = "/announcement/" + post.getId();
        } else {
            noticeUrl = "/question/" + post.getId();
        }
        noticeService.create(noticeMessage, noticeUrl, postOwner);

        return commentRepository.save(comment);
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + id));
    }
}
