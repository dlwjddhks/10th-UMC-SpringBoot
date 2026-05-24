package com.example.umc10th.domain.user.success;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum SuccessCode implements BaseSuccessCode {
    USER_SIGNUP_SUCCESS(HttpStatus.OK, "USER2001", "회원가입 성공"),
    LOGIN_SUCCESS(HttpStatus.OK, "USER2002", "로그인 성공"),
    USER_INFO_SUCCESS(HttpStatus.OK, "USER2003", "회원 정보 조회 성공"),
    HOME_SUCCESS(HttpStatus.OK, "HOME2001", "홈화면조회성공"),
    OK(HttpStatus.OK, "COMMON200", "성공입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
