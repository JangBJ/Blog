package com.hodol.api.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //PK

    private String title;

    @Lob    // DB에서 LongString 형태로 넘어가도록 생성해주게 해줌
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }


    // build하지 않은 클래스 자체를 넘겨준다? 이게 좋은건가?
    public PostEditor.PostEditorBuilder toEditor(){
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }
}
