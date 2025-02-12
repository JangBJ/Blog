package com.hodol.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Login {

    @NotBlank(message = "이메일을 입력해 주세요")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private final String password;

    @Builder
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
