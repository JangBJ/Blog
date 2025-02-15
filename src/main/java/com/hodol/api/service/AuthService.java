package com.hodol.api.service;

import com.hodol.api.Exception.InvalidSigninInformation;
import com.hodol.api.domain.Session;
import com.hodol.api.domain.User;
import com.hodol.api.repository.UserRepository;
import com.hodol.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login login){
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        Session session = user.addSession();

        return session.getAccessToken();
    }

}
