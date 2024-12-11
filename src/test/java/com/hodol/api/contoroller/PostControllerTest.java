package com.hodol.api.contoroller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/post 요청시 Hello World를 출력한다.")
    void test() throws Exception{
        // 우리가 글쓰기 할때 보내는 데이터
        // 글 제목
        // 글 내 용
        

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)    // 이걸로 JSON타입으로 바꾸는것?
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk()) // 상태코드가 200이어야함
                .andExpect(content().string("Hello World"))//결과값을 기대한다
                .andDo(print()); // 테스트에 대한 전반적인 요청 서머리 알고 싶을 때 사용
    }

    @Test
    @DisplayName("/post 요청시 Title은 필수다")
    void test2() throws Exception{
        // 우리가 글쓰기 할때 보내는 데이터
        // 글 제목
        // 글 내 용


        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)    // 이걸로 JSON타입으로 바꾸는것?
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk()) // 상태코드가 200이어야함
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력하세요."))//결과값을 기대한다
                .andDo(print()); // 테스트에 대한 전반적인 요청 서머리 알고 싶을 때 사용
    }
}