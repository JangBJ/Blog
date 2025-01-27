package com.hodol.api.request;

import com.hodol.api.Exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class  PostCreate {

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

    public void validate(){
        // 만약에 정책으로 금지어가 있다면?
        if(title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}
