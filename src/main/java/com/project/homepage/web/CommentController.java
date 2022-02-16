package com.project.homepage.web;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.CommentService;
import com.project.homepage.service.PostService;
import com.project.homepage.web.dto.CommentSaveRequestDto;
import com.project.homepage.web.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class CommentController {

    private final PostService postsService;
    private final CommentService commentsService;

    @PostMapping("/{postId}/comment")
    public Comment saveComment(@PathVariable Long postId,
                               @RequestBody CommentSaveRequestDto dto, @Login User user) {
        Post post = postsService.findById(postId);

        Comment comment = dto.toEntity();
        comment.setPost(post);
        comment.setUser(user);
        log.info("postId: {}", post.getId());

        return commentsService.save(comment);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentsService.delete(commentsService.findById(id));
    }


}
