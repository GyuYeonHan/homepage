package com.project.homepage.web.dto.post;

import com.project.homepage.domain.post.Post;
import com.project.homepage.util.TimeUtil;
import lombok.Data;

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
        this.modifiedDate = TimeUtil.calculateTimeBeforeNow(post.getModifiedDate());
    }
}
