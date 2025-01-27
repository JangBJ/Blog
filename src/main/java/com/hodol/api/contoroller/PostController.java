package com.hodol.api.contoroller;


import com.hodol.api.request.PostEdit;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.hodol.api.request.PostCreate;
import com.hodol.api.service.PostService;
import com.hodol.api.response.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 글 조회
    @GetMapping("/posts/{postId}")  //get(@PathVariable Long postId)하면 postId를 받는것
    public PostResponse get(@PathVariable(name = "postId") Long id){ //포스트id로 받는게 아닌 id로 받을수 있음
        return postService.get(id); //PostService의 실제 글을 가져오는 메서드
    }

    // 글 전체 조회
    @GetMapping("/posts")
    public List<PostResponse> getAll(Pageable pageable){
        return postService.getAll(pageable);
    }

    // 글 수정
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable(name = "postId") Long id, @RequestBody @Valid PostEdit request){
        postService.edit(id, request);
    }

    // 글 삭제
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable(name = "postId") Long id){
        postService.deletePost(id);
    }

}
