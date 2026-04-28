package com.example.umc10th.domain.user.success;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum SuccessCode implements BaseSuccessCode {
    USER_SIGNUP_SUCCESS(HttpStatus.OK, "USER2001", "회원가입 성공"),
    HOME_SUCCESS(HttpStatus.OK, "HOME2001", "홈화면조회성공");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
