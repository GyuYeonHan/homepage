package com.project.homepage.web;

import com.project.homepage.domain.user.User;
import com.project.homepage.service.UserService;
import com.project.homepage.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String adminHome() {
        return "admin/home";
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<UserResponseDto> userDtoList = userService.findAllUser().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("userList", userDtoList);

        return "admin/users";
    }
}
