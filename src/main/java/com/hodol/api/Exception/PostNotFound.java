package com.hodol.api.Exception;

/**
 *
 * status -> 404
 */
public class PostNotFound extends HodologException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    // 생성자 오버로딩
    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getSatusCode() {
        return 404;
    }
}
