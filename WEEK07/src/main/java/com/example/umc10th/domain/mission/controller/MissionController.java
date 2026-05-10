package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.request;
import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.domain.mission.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;
    @GetMapping("/missions")
    public ApiResponse<response.MissionPageDTO> getMissions(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ApiResponse.onSuccess(
                SuccessCode.MISSION_GET_SUCCESS,
                missionService.getMyMissions(userId, page)
        );
    }
    @PatchMapping("/user-missions/{userMissionId}")
    public ApiResponse<Void> completeMission(
            @PathVariable Long userMissionId,
            @RequestBody @Valid request.UpdateMissionDTO req
    ) {

        return ApiResponse.onSuccess(
                SuccessCode.MISSION_COMPLETE_SUCCESS,
                null
        );
    }
}