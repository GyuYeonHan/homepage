package com.project.homepage;

import com.project.homepage.domain.user.Role;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class ProdDataInit {


    private final UserService userService;

    private User user;

    /**
     * 관리자 계정 추가
     */
    @PostConstruct
    public void init() {
        userInit();
    }

    private void userInit() {
        user = User.builder()
                .username("관리자")
                .role(Role.ADMIN)
                .loginId("admin")
                .password("admin")
                .build();

        userService.save(user);
    }

}
