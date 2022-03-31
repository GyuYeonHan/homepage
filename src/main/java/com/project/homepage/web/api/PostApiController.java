package com.project.homepage.web.api;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.CommentService;
import com.project.homepage.service.NoticeService;
import com.project.homepage.service.PostService;
import com.project.homepage.web.dto.comment.CommentResponseDto;
import com.project.homepage.web.dto.post.PostCommentDto;
import com.project.homepage.web.dto.post.PostEditRequestDto;
import com.project.homepage.web.dto.post.PostResponseDto;
import com.project.homepage.web.dto.post.PostSaveRequestDto;
import com.project.homepage.web.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final NoticeService noticeService;

//    @GetMapping
//    public ResponseEntity<List<PostResponseDto>> getAllPostList() {
//        List<PostResponseDto> postDtoList = postService.findAllPost().stream()
//                .map(PostResponseDto::new)
//                .collect(Collectors.toList());
//
//        HttpHeaders headers = new HttpHeaders();
//
//        return new ResponseEntity<>(postDtoList, headers, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPostList() {
        List<PostResponseDto> postDtoList = postService.findAllPost().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(postDtoList, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody PostSaveRequestDto dto, @Login User user) {
        if (UserNotAuthentication(user)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Post post = dto.toEntity();
        post.setUser(user);
        postService.save(post);

        Notice notice = Notice.builder()
                .message("새 글이 생성되었습니다.")
                .url("/post/" + post.getId())
                .status(NoticeStatus.UNREAD)
                .user(user)
                .build();
        noticeService.create(notice);

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostCommentDto> getPost(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        PostResponseDto postDto = new PostResponseDto(post);
        List<CommentResponseDto> commentDtoList = post.getCommentList().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        PostCommentDto data = new PostCommentDto(postDto, commentDtoList);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId, @RequestBody PostEditRequestDto dto, @Login User user) {
        if (UserNotAuthentication(user) || UserNotAuthorization(postId, user))
            return new ResponseEntity<>("You are not authorized", HttpStatus.UNAUTHORIZED);
        postService.edit(postId, dto.getTitle(), dto.getContent());

        return new ResponseEntity<>("Edit Post Success", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @Login User user) {
        if (UserNotAuthentication(user) || UserNotAuthorization(postId, user))
            return new ResponseEntity<>("You are not authorized", HttpStatus.UNAUTHORIZED);

        Post post = postService.findById(postId);
        List<Comment> commentList = post.getCommentList();

        for (Comment comment : commentList) {
            commentService.delete(comment);
        }

        postService.delete(postService.findById(postId));

        return new ResponseEntity<>("Delete Post Success", HttpStatus.OK);
    }

    private boolean UserNotAuthentication(User user) {
        return user == null;
    }

    private boolean UserNotAuthorization(@PathVariable Long postId, @Login User user) {
        Post post = postService.findById(postId);
        if (user.getRole().equals(Role.ADMIN)) {
            return false;
        }

        return !post.getUser().getId().equals(user.getId());
    }
}
