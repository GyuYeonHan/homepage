package com.project.homepage.web.api;

import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.UserService;
import com.project.homepage.web.dto.user.UserResponseDto;
import com.project.homepage.web.dto.user.UserSaveRequestDto;
import com.project.homepage.web.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
        try {
            User findUser = userService.findById(userId);
            return new ResponseEntity<>(new UserResponseDto(findUser), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (MethodArgumentTypeMismatchException e) {
            log.info("error", e);
            throw e;
        }
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody UserSaveRequestDto dto, @Login User user, UriComponentsBuilder uriBuilder) {
        if (UserNotAuthentication(user) || UserNotAuthorization(user)) {
            return new ResponseEntity<>("[USER ADD] You are not Authorized", HttpStatus.UNAUTHORIZED);
        }

        User createdUser = userService.save(dto.toEntity());

        URI location = uriBuilder.path("/api/user/{id}")
                .buildAndExpand(createdUser.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>("Add User Success", headers, HttpStatus.CREATED);
    }

    private boolean UserNotAuthentication(User user) {
        return user == null;
    }

    private boolean UserNotAuthorization(@Login User user) {
        return !user.getRole().equals(Role.ADMIN);
    }
}
