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

    // 10글자 제한
    public String getTitle(){
        return (this.title.length() <=10) ? this.title:this.title.substring(0,10);
    }

}
