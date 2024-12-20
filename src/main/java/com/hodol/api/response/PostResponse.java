package com.hodol.api.response;

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
        return this.title.substring(0,10);
    }
}
