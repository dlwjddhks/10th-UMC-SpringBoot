package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.request;
import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MissionController {

    @GetMapping("/missions")
    public ApiResponse<response.MissionListResDTO> getMissions(
            @RequestParam Long userId,
            @RequestParam(required = false) Status status,
            @RequestParam int page
    ) {

        return ApiResponse.onSuccess(
                SuccessCode.MISSION_GET_SUCCESS,
                new response.MissionListResDTO(null,null,null)
        );
    }
    @PatchMapping("/user-missions/{userMissionId}")
    public ApiResponse<Void> completeMission(
            @PathVariable Long userMissionId,
            @RequestBody request.UpdateMissionDTO req
    ) {

        return ApiResponse.onSuccess(
                SuccessCode.MISSION_COMPLETE_SUCCESS,
                null
        );
    }
}