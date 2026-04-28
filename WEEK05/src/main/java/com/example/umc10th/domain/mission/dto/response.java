package com.example.umc10th.domain.mission.dto;
import com.example.umc10th.domain.mission.enums.Status;
public class response {
    public record MissionListResDTO(
            Long missionId,
            String name,
            Status status
    ) {}
    public record MissionCompleteResult(
            Long userMissionId,
            Status status
    ) {}
}
