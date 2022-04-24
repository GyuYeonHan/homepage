package com.project.homepage.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.homepage.domain.post.Post;
import com.project.homepage.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
//@SpringBootTest
@WebMvcTest(PostApiController.class)
@AutoConfigureMockMvc
@DisplayName("Post API Test")
class PostApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("1번 포스트 조회")
    void viewPost_No1() throws Exception {
        //Given
        String url = "/api/post/1";
        given(postService.findById(1L))
                .willReturn(
                        Post.builder()
                                .id(1L)
                                .title("Test Post")
                                .content("Test Post Content")

                                .build());

        //When
        ResultActions resultActions = mockMvc.perform(
                get(url).accept(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("title1"))
                .andExpect(jsonPath("content").value("test post content"))
                .andExpect(jsonPath("username").value("test1"));
    }

}