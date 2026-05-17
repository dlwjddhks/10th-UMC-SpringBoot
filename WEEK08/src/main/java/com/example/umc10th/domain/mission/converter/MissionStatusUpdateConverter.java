package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.dto.response;

public class MissionStatusUpdateConverter {

    public static response.MissionCompleteResult toCompleteResult(
            UserMission userMission
    ) {
        return new response.MissionCompleteResult(
                userMission.getUserMissionId(),
                userMission.getStatus()
        );
    }
}