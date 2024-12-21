package com.hodol.api.contoroller;

import com.hodol.api.domain.Post;
import com.hodol.api.request.PostCreate;
import com.hodol.api.response.PostResponse;
import com.hodol.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // 글 등록
    private final PostService postService;

    // Case1. 저장한 데이터 Entity -> response로 응답하기
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    /* 글조회
        /posts -> 글 전체 조회(검색 + 페이징)하는 API
        /posts/{postId} -> 글 한개만 조회하는 API (즉 어떤 특정 리소스를 가져오는)
     */

    @GetMapping("/posts/{postId}")  //get(@PathVariable Long postId)하면 postId를 받는것
    public PostResponse get(@PathVariable(name = "postId") Long id){ //포스트id로 받는게 아닌 id로 받을수 있음
        PostResponse response = postService.get(id); //PostService의 실제 글을 가져오는 메서드
        return response;

    }
}
