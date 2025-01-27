package com.hodol.api.contoroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodol.api.domain.Post;
import com.hodol.api.repository.PostRepository;
import com.hodol.api.request.PostCreate;
import com.hodol.api.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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

    private void createPost(){
        List<Post> posts = IntStream.range(0, 30)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("우히히" + i)
                            .content("재밌당" + i)
                            .build();
                })
                .collect(Collectors.toList());

        postRepository.saveAll(posts);
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
                        .contentType(APPLICATION_JSON)    // 이걸로 JSON타입으로 바꾸는것
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

    @Test
    @DisplayName("글 전체 조회")
    void test5() throws Exception {
        Post post = Post.builder()
                .title("title1")
                .content("content1")
                .build();
        postRepository.save(post);

        Post post1 = Post.builder()
                .title("title2")
                .content("content2")
                .build();
        postRepository.save(post1);

        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(2) )) // 내려온 데이터 개수
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andExpect(jsonPath("$[0].content").value("content1"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지")
    void test6() throws Exception {

        createPost();

        mockMvc.perform(get("/posts?page=1&sort=id,desc")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].title").value("우히히29"))
                .andExpect(jsonPath("$[0].content").value("재밌당29"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test7() throws Exception {

        // given
        Post post = Post.builder()
                .title("우히히")
                .content("재밌당")
                .build();

        postRepository.save(post);

        PostEdit edit = PostEdit.builder()
                .title("ㅇㅇㅇ")
                .content("재밌당")
                .build();

        mockMvc.perform(patch("/posts/{postId}", post.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(edit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 삭제")
    void test8() throws Exception {

        Post post = Post.builder()
                .title("히히")
                .content("재밌당")
                .build();

        postRepository.save(post);

        mockMvc.perform(delete("/posts/{postId}", post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {

        mockMvc.perform(delete("/posts/{postId}", 1L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}