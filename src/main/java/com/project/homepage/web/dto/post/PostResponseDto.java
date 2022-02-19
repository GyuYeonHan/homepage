package com.project.homepage.web.dto.post;

import com.project.homepage.domain.post.Post;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private String modifiedDate;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.username = entity.getUser().getUsername();
        this.modifiedDate = entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
    }
}
