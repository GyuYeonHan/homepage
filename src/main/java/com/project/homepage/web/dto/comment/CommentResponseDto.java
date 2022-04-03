package com.project.homepage.web.dto.comment;

import com.project.homepage.domain.Comment;
import com.project.homepage.util.TimeUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentResponseDto {

    private Long id;

    @NotBlank
    private String content;

    private String username;

    private String modifiedDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.modifiedDate = TimeUtil.calculateTimeBeforeNow(comment.getModifiedDate());
    }
}
