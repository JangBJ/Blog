package com.hodol.api.contoroller;

import com.hodol.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
/*            FieldError fieldError = e.getFieldError();
            String field = fieldError.getField();           // 무슨 필드가 잘못된 필드인지
            String message = fieldError.getDefaultMessage();// 에러 메시지 호출*/
        ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다");

        // 리스트에 에러을 담음 e.getFieldErrors()
        for(FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }
}

