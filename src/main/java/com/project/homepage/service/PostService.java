package com.project.homepage.service;

import com.project.homepage.domain.post.Post;
import com.project.homepage.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void edit(Long id, String title, String content) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        post.update(title, content);
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }

//    @Transactional(readOnly = true)
//    public List<Post> findAllPost() {
//        return postRepository.findAllDesc();
//    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPost() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        return postRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    }
}
