package com.project.homepage.web.dto.post;

import com.project.homepage.domain.post.Post;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class PostIdResponseDto {

    private Long id;

    public PostIdResponseDto(Post entity) {
        this.id = entity.getId();
    }
}
