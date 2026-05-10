package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.Status;
import jakarta.validation.constraints.NotNull;

public class request {
    public record UpdateMissionDTO(
            @NotNull(message = "status는 필수입니다.")
            Status status
    ) {}
    public record MissionRequestDTO(
            @NotNull(message = "userId는 필수입니다.")
            Long userId
    ) {}
}
