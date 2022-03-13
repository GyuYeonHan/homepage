package com.project.homepage.web.dto.comment;

import com.project.homepage.domain.Comment;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

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
        this.modifiedDate = comment.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));;
    }
}
