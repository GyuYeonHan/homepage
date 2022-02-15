package com.project.homepage.web.dto;

import com.project.homepage.domain.post.Post;
import lombok.Data;

@Data
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.username = entity.getUser().getUsername();
    }
}
