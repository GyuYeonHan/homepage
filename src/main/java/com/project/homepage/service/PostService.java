package com.project.homepage.service;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.repository.CommentRepository;
import com.project.homepage.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void delete(Long id) {
        Post posts = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        List<Comment> commentList = posts.getCommentList();

        for (Comment comment : commentList) {
            commentRepository.delete(comment);
        }

        postRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllDesc() {
        return postRepository.findAllDesc();
    }


}
