package com.hodol.api.response;

import com.hodol.api.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
 * 서비스 정책에 맞는 클래스
 */

@Getter
@Builder
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    public String getTitle() {
        return (this.title.length() <=10) ? this.title : this.title.substring(0,10);
    }

    public static PostResponse fromPost(Post post){

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;
    }
}


