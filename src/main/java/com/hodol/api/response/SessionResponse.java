package com.hodol.api.response;

import lombok.Getter;

@Getter
public class SessionResponse {

    String accessToken;

    public SessionResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
