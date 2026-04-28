package com.example.umc10th.domain.user.dto;

import java.util.List;

public class response {
    public record SignUpResult(
            Long userId,
            String name,
            String phone_number,
            String nickname
    ){}
    public record HomeMissionSummary(
            Long missionId,
            String name,
            String status
    ) {}

    public record HomeResponse(
            Long userId,
            String nickname,
            List<HomeMissionSummary> missions
    ) {}
}
