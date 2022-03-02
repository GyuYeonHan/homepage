package com.project.homepage.web.dto.comment;

import com.project.homepage.domain.Comment;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentSaveRequestDto {

    @NotBlank
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
