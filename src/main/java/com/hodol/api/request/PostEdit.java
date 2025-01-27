package com.hodol.api.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostEdit {

    @NotBlank(message = "타이틀을 입력하십시오")
    String title;

    @NotBlank(message = "콘텐츠를 입력하십시오")
    String content;
}
