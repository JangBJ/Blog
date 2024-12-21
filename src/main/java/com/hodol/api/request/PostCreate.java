package com.hodol.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

// DTO?
@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력하세요.")
    private final String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private final String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    // 외부에서 값을 변경하고 싶을때
    public PostCreate changTitle(String title){
        return PostCreate.builder().title(title).content(content).build();
    }
}
