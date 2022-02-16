package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(Comment comment) {
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
