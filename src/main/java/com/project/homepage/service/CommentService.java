package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public Comment save(Comment comment) {
        Post post = comment.getPost();
        User commentOwner = comment.getUser();
        User postOwner = comment.getPost().getUser();

        createNotice(post, commentOwner.getUsername(), postOwner);

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + id));
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    private Notice createNotice(Post post, String commentOwnerUserName, User postOwner) {
        String message = "글[" + post.getTitle() + "]에 " + commentOwnerUserName + "님의 댓글이 달렸습니다.";
        String url = post.getType().getUrl() + "/" + post.getId();

        Notice notice = Notice.create(message, url, postOwner);

        noticeRepository.save(notice);

        return notice;
    }
}
