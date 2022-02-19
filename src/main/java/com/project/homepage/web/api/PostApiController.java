package com.project.homepage.web.api;

import com.project.homepage.service.PostService;
import com.project.homepage.web.dto.post.PostResponseDto;
import com.project.homepage.web.dto.post.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping
    public List<PostResponseDto> getAllPostList() {
        List<PostResponseDto> postDtoList = postService.findAllPost().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return postDtoList;
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
