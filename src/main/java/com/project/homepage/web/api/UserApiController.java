package com.project.homepage.web.api;

import com.project.homepage.service.UserService;
import com.project.homepage.web.dto.post.PostResponseDto;
import com.project.homepage.web.dto.post.PostSaveRequestDto;
import com.project.homepage.web.dto.user.UserResponseDto;
import com.project.homepage.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUserList() {
        List<UserResponseDto> userDtoList = userService.findAllUser().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());

        return userDtoList;
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return new UserResponseDto(userService.findById(userId));
    }

    @PostMapping
    public Long saveUser(UserSaveRequestDto dto) {
        return userService.save(dto.toEntity()).getId();
    }
}
