package com.hodol.api.Exception;

public class Unauthorized extends HodologException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getSatusCode() {
        return 401;
    }
}
