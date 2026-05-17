package com.example.umc10th.domain.user.enums;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4001", "유저가 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_4002", "이미 존재하는 이메일입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
