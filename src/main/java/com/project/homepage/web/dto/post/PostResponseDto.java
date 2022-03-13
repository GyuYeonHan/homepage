package com.project.homepage.web.dto.post;

import com.project.homepage.domain.post.Post;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class PostResponseDto {

    private String id;
    private String title;
    private String content;
    private String username;
    private String modifiedDate;

    public PostResponseDto(Post post) {
        this.id = post.getId().toString();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.modifiedDate = post.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
    }
}
