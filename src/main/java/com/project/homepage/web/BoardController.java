package com.project.homepage.web;

import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.PostService;
import com.project.homepage.web.dto.comment.CommentSaveRequestDto;
import com.project.homepage.web.dto.post.PostEditRequestDto;
import com.project.homepage.web.dto.post.PostResponseDto;
import com.project.homepage.web.dto.post.PostSaveRequestDto;
import com.project.homepage.web.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;

    @GetMapping
    public String boardHome(Model model) {
        List<PostResponseDto> postDtoList = postService.findAllPost().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        model.addAttribute("postList", postDtoList);

        return "board/board";
    }

    @GetMapping("/save")
    public String savePostForm(Model model) {
        PostSaveRequestDto dto = new PostSaveRequestDto();

        model.addAttribute("post", dto);
        return "board/savePostForm";
    }

    @PostMapping("/save")
    public String savePost(@Validated @ModelAttribute("post") PostSaveRequestDto dto, BindingResult bindingResult, @Login User user) {
        if (bindingResult.hasErrors()) {
            return "board/savePostForm";
        }

        Post post = dto.toEntity();
        post.setUser(user);
        postService.saveAnnouncement(post);

        return "redirect:/board";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        PostResponseDto postDto = new PostResponseDto(post);
        List<Comment> commentsList = post.getCommentList();
        List<String> content = Arrays.asList(post.getContent().split("\n"));

        model.addAttribute("post", postDto);
        model.addAttribute("postContent", content);
        model.addAttribute("comments", commentsList);

        CommentSaveRequestDto commentDto = new CommentSaveRequestDto();
        model.addAttribute("commentForm", commentDto);

        return "board/viewPost";
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        PostEditRequestDto postDto = new PostEditRequestDto(post.getTitle(), post.getContent());
        model.addAttribute("post", postDto);
        model.addAttribute("postId", id);

        return "board/editPostForm";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable Long id, @Validated @ModelAttribute("post") PostEditRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/editPostForm";
        }
        postService.edit(id, dto.getTitle(), dto.getContent());

        return "redirect:/board/{id}";
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.delete(postService.findById(id));

        return "redirect:/board";
    }
}
