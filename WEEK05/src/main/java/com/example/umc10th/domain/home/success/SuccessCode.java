package com.example.umc10th.domain.home.success;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum SuccessCode implements BaseSuccessCode {
    HOME_SUCCESS(HttpStatus.OK, "HOME2001", "홈 화면 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}