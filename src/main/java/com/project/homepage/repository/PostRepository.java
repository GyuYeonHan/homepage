package com.project.homepage.repository;

import com.project.homepage.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p order by p.id desc")
    List<Post> findAllDesc();
}
