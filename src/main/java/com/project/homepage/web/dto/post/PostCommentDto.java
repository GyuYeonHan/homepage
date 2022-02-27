package com.project.homepage.web.dto.post;

import com.project.homepage.web.dto.comment.CommentResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class PostCommentDto {
    private PostResponseDto post;
    private List<CommentResponseDto> commentList;

    public PostCommentDto(PostResponseDto postDto, List<CommentResponseDto> commentDtoList) {
        this.post = postDto;
        this.commentList = commentDtoList;
    }
}
