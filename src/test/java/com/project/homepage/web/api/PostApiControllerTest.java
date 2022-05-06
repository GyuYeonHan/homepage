package com.project.homepage.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
//@WebMvcTest(PostApiController.class)
@AutoConfigureMockMvc
@DisplayName("Post API 테스트")
class PostApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    @DisplayName("전체 포스트 조회")
    void viewAllPost() throws Exception {
        //Given
        String url = "/api/post";

        //When
        ResultActions resultActions = mockMvc.perform(
                get(url).accept(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0].id").value(3))
                .andExpect(jsonPath("$.[0].title").value("title3"))
                .andExpect(jsonPath("$.[0].content").value("test post content"))
                .andExpect(jsonPath("$.[0].username").value("test3"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[2].id").value(1));

    }

    @Test
    @DisplayName("1번 포스트 조회")
    void viewPost_No1() throws Exception {
        //Given
        String url = "/api/post/1";

        //When
        ResultActions resultActions = mockMvc.perform(
                get(url).accept(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.post.id").value("1"))
                .andExpect(jsonPath("$.post.title").value("title1"))
                .andExpect(jsonPath("$.post.content").value("test post content"))
                .andExpect(jsonPath("$.post.username").value("test1"));
    }

}