package com.hodol.api.contoroller;


import com.hodol.api.config.data.UserSession;
import com.hodol.api.request.PostCreate;
import com.hodol.api.request.PostEdit;
import com.hodol.api.response.PostResponse;
import com.hodol.api.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/foo")
    public Long foo(UserSession userSession) {
        log.info(">>{}", userSession.Id);
        return userSession.Id;
    }

    @GetMapping("/bar")
    public String bar() {
        return "인증이 필요없는 페이지";
    }

    // Case1. 저장한 데이터 Entity -> response로 응답하기
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {

        // 1. GET Parameter로 받기
        // 2. POST(body) value
        // 3. Header
        request.validate();
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
