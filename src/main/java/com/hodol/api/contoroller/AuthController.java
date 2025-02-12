package com.hodol.api.contoroller;

import com.hodol.api.Exception.InvalidSigninInformation;
import com.hodol.api.domain.User;
import com.hodol.api.repository.UserRepository;
import com.hodol.api.request.Login;
import com.hodol.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public void login(@RequestBody Login login) {
        log.info(">>>>", login);
        authService.signin(login);
    }
}
