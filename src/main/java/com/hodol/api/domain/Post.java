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

    public String getTitle(){
        // 엔티티 클래스에서 Getter 메서드를 만들때 가능하면
        // 서비스에 맞는 서비스의 정책을 넣지말기!!!!!! 절대로!!!!!!!!!!!
        return this.title.substring(0,10);
    }



}
