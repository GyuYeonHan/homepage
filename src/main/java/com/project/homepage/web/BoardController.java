package com.project.homepage.web;

import com.project.homepage.domain.post.Post;
import com.project.homepage.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;

    @GetMapping
    public String boardHome(Model model) {
        List<Post> postList = postService.findAllDesc();
        model.addAttribute("postList", postList);

        return "board";
    }
}
