package com.hodol.api.config.data;

// 인증이 필요한 애들한테만 넘겨주는 DTO
public class UserSession {

    public final Long Id;

    public UserSession(Long id) {
        Id = id;
    }
}
