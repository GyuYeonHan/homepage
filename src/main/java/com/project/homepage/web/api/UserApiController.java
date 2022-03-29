package com.project.homepage.web.api;

import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.UserService;
import com.project.homepage.web.dto.user.UserResponseDto;
import com.project.homepage.web.dto.user.UserSaveRequestDto;
import com.project.homepage.web.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUserList() {
        return new ResponseEntity<>(userService.findAllUser().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(new UserResponseDto(userService.findById(userId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserSaveRequestDto dto, @Login User user) {
        if (UserNotAuthentication(user) || UserNotAuthorization(user)) {
            return new ResponseEntity<>("[USER ADD] You are not Authorized", HttpStatus.UNAUTHORIZED);
        }

        userService.save(dto.toEntity());
        return new ResponseEntity<>("Add User Success", HttpStatus.CREATED);
    }

    private boolean UserNotAuthentication(User user) {
        return user == null;
    }

    private boolean UserNotAuthorization(@Login User user) {
        return !user.getRole().equals(Role.ADMIN);
    }
}
