package com.hodol.api.Exception;

public class InvalidSigninInformation extends HodologException {

    private static final String MASSAGE = "아이디/비밀번호가 올바르지 않습니다";

    public InvalidSigninInformation() {
        super(MASSAGE);
    }

    @Override
    public int getSatusCode() {
        return 400;
    }
}
