package com.project.homepage.web.api;

import com.project.homepage.service.PostService;
import com.project.homepage.web.dto.PostResponseDto;
import com.project.homepage.web.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping
    public List<PostResponseDto> getAllPostList() {
        List<PostResponseDto> postDtoList = postService.findAllDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return postDtoList;
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return new PostResponseDto(postService.findById(postId));
    }

    @PostMapping
    public Long savePost(PostSaveRequestDto dto) throws IOException {
        return postService.save(dto.toEntity()).getId();
    }
}
