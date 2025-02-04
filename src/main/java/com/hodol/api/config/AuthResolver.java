package com.hodol.api.config;

import com.hodol.api.Exception.Unauthorized;
import com.hodol.api.config.data.UserSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class); // 인가할때 UserSession 클래스가 맞는지 확인하는 작업 수단
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization");
        if(accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        // DB에서 사용자 있는지 확인 작업 가능


        return new UserSession(1L);
    }
}
