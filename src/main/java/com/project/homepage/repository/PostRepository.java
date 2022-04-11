package com.project.homepage.repository;

import com.project.homepage.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p order by p.id desc")
    List<Post> findAllPostDesc();

    @Query("select p from Post p where p.type = com.project.homepage.domain.post.PostType.ANNOUNCEMENT order by p.id desc")
    List<Post> findAllAnnouncementPostDesc();

    @Query("select p from Post p where p.type = com.project.homepage.domain.post.PostType.QUESTION order by p.id desc")
    List<Post> findAllQuestionPostDesc();

    Page<Post> findAll(Pageable pageable);
}
