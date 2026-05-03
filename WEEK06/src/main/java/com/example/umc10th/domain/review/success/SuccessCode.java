package com.example.umc10th.domain.review.success;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum SuccessCode implements BaseSuccessCode {
    REVIEW_CREATE_SUCCESS(HttpStatus.OK, "REVIEW2001", "리뷰 작성 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
