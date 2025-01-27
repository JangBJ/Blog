package com.hodol.api.Exception;

// 잘못된 요청시 사용

import lombok.Getter;

/**
 * 정책상 status를 400
 */
@Getter
public class InvalidRequest extends HodologException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() { super(MESSAGE); }


    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getSatusCode() {
        return 400;
    }

}
