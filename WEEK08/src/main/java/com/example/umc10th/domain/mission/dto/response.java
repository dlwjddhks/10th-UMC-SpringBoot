package com.example.umc10th.domain.mission.dto;
import com.example.umc10th.domain.mission.enums.Status;
import lombok.Builder;

import java.util.List;

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
    public record MissionPageDTO(
            List<MissionListResDTO> missionList,
            long totalCount,
            boolean firstPage,
            boolean lastPage,
            int totalPages,
            int pageSize
    ) {}
    @Builder
    public record GetMission(
            Long missionId,
            Integer rewardPoint
    ){}
}
