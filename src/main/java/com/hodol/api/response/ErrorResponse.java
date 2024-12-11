package com.hodol.api.response;

/*
   {
        code: 400
        message: "잘못된 요청입니다"
        validation: {
            title: "값을 입력해주세요"
        }   어떤 필드가 잘못됐는지 알려주려면 Validation Object나 배열을 하나 만들어서 배열 형태로 박스를 만들어서
   }
   이런식으로 변환이 되게 만듬
* */

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;

    // private Map<String, String> validation; // 이렇게 만들면 초기값이 null이라서 밑처럼 HashMap으로 만들어줌
    private Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}