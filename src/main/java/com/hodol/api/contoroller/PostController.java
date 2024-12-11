package com.hodol.api.contoroller;

import com.hodol.api.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class PostController {
    // 글 등록

    @PostMapping("/posts")
    public Map<String ,String> post(@RequestBody @Valid PostCreate params) {
        // db에 저장할 때는 넘어온 데이터를 뭔가 저장 시키는 메소드 기능이
        // 보통은 넘어온 데이터를 어떤 repository를 만들어서 저장 repository.sava(params)
        // 하지만 가능한 서비스 레이어를 하나 만들고 그 서비스 레이어에서 repository호출하기


        return Map.of();
    }
}
