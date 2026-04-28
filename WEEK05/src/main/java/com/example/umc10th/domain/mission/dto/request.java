package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.Status;

public class request {
    public record UpdateMissionDTO(
            Status status
    ) {}
}
