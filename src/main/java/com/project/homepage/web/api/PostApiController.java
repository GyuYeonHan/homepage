package com.project.homepage.web.api;

import com.project.homepage.service.PostService;
import com.project.homepage.web.dto.post.PostResponseDto;
import com.project.homepage.web.dto.post.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @Value("${origin}")
    private String origin;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPostList() {
        List<PostResponseDto> postDtoList = postService.findAllPost().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.setAccessControlAllowOrigin(origin);

        return new ResponseEntity<>(postDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return new PostResponseDto(postService.findById(postId));
    }

    @PostMapping
    public Long savePost(PostSaveRequestDto dto) {
        return postService.save(dto.toEntity()).getId();
    }
}
