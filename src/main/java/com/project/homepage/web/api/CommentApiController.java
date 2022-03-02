package com.project.homepage.web.api;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.CommentService;
import com.project.homepage.service.PostService;
import com.project.homepage.web.dto.comment.CommentSaveRequestDto;
import com.project.homepage.web.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentApiController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> saveComment(@PathVariable Long postId, @RequestBody CommentSaveRequestDto dto, @Login User user) {
        if (UserNotAuthentication(user)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Post post = postService.findById(postId);

        Comment comment = dto.toEntity();
        comment.setPost(post);
        comment.setUser(user);
        commentService.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @Login User user) {
        if (UserNotAuthentication(user) || UserNotAuthorization(commentId, user))
            return new ResponseEntity<>("You are not authorized", HttpStatus.UNAUTHORIZED);
        commentService.delete(commentService.findById(commentId));

        return new ResponseEntity<>("Delete Comment Success", HttpStatus.OK);
    }

    private boolean UserNotAuthentication(User user) {
        return user == null;
    }

    private boolean UserNotAuthorization(Long commentId, User user) {
        Comment comment = commentService.findById(commentId);
        if (user.getRole().equals(Role.ADMIN)) {
            return false;
        }

        return !comment.getUser().getId().equals(user.getId());
    }
}
