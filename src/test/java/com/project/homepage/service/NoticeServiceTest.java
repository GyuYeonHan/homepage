package com.project.homepage.service;

import com.project.homepage.domain.user.User;
import com.project.homepage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoticeServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    NoticeService noticeService;
    @Autowired
    PostService postService;

    private User user;

    @Test
    void create() {
        //given


        //when


        //then
    }
}