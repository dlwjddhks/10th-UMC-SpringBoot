package com.example.umc10th.domain.mission.success;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements BaseSuccessCode {
    MISSION_GET_SUCCESS(HttpStatus.OK, "MISSION2001", "미션 목록 조회 성공"),
    MISSION_COMPLETE_SUCCESS(HttpStatus.OK, "MISSION2002", "미션 완료 성공"),
    MISSION_CREATE_SUCCESS(HttpStatus.OK,"MISSION2003","성공적으로 미션을 생성했습니다."),
    MISSION_GET_STORE_SUCCESS(HttpStatus.OK, "MISSION2004", "성공적으로 가게 미션을 조회했습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
