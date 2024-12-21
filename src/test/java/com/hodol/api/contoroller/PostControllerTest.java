package com.hodol.api.contoroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodol.api.domain.Post;
import com.hodol.api.repository.PostRepository;
import com.hodol.api.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;  // JSON이 DB에 결국 저장되어야하기 때문에 레포지토리를 주입

    @BeforeEach
    void clean(){ // 깨끗한 테스트를 위해서 Repository를 비워주는 메서드
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/post 요청시 Hello World를 출력한다.")
    void test() throws Exception{
        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

         String json = objectMapper.writeValueAsString(request); // 파라미터러 온 것을 빈 규약에 따라 JSON형태로 가공해줌

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)    // 이걸로 JSON타입으로 바꾸는것?
                        .content(json))

                .andExpect(status().isOk()) // 상태코드가 200이어야함
                .andExpect(content().string(""))//결과값을 기대한다
                .andDo(print()); // 테스트에 대한 전반적인 요청 서머리 알고 싶을 때 사용
    }

    @Test
    @DisplayName("/post 요청시 Title은 필수다")
    void test2() throws Exception{
        // given
        PostCreate request = PostCreate.builder().content("내용입니다.").build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)    // 이걸로 JSON타입으로 바꾸는것?
                        .content(json))

                .andExpect(status().isBadRequest()) // 상태코드가 200이어야함
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력하세요."))//결과값을 기대한다
                .andDo(print()); // 테스트에 대한 전반적인 요청 서머리 알고 싶을 때 사용
    }


    @Test
    @DisplayName("/post 요청시 DB에 값을 저장한다.")
    void test3() throws Exception{
        PostCreate request = PostCreate.builder().title("제목입니다.").content("내용입니다.").build();

        // Jackson (JSON을 프로세싱 해주는 라이브러리)
        String json = objectMapper.writeValueAsString(request); // 파라미터러 온 것을 빈 규약에 따라 JSON형태로 가공해줌

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)    // 이걸로 JSON타입으로 바꾸는것?
                        .content(json))

                .andExpect(status().isOk()) // 상태코드가 200이어야함
                .andDo(print()); // 테스트에 대한 전반적인 요청 서머리 알고 싶을 때 사용
        assertEquals (1L,postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception{
        //given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);

        //expected(when + then)
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }
}