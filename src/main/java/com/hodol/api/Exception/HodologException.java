package com.hodol.api.Exception;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

// 최상위 예외 처리 클래
@Getter
public abstract class HodologException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public HodologException(String message) {
        super(message);
    }

    public HodologException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getSatusCode();

    public void addValidation(String fieldName, String message) {

        validation.put(fieldName, message);
    }
}
