package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.entity.UserMission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

    // 단일 미션 DTO 변환
    public static response.MissionListResDTO toMissionDTO(
            UserMission mission
    ) {
        return new response.MissionListResDTO(
                mission.getMission().getMissionId(),
                mission.getMission().getName(),
                mission.getStatus()
        );
    }

    // 페이지 전체 변환
    public static response.MissionPageDTO toMissionPageDTO(
            Page<UserMission> page
    ) {

        List<response.MissionListResDTO> missionList =
                page.getContent().stream()
                        .map(MissionConverter::toMissionDTO)
                        .toList();

        return new response.MissionPageDTO(
                missionList,
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getNumberOfElements()
        );
    }
}