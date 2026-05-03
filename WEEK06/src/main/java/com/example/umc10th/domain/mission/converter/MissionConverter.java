package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static List<response.MissionListResDTO> toMissionList(
            List<UserMission> missions
    ) {
        return missions.stream()
                .map(m -> new response.MissionListResDTO(
                        m.getMission().getMissionId(),
                        m.getMission().getName(),
                        m.getStatus()
                ))
                .toList();
    }
}