package com.example.umc10th.domain.user.dto;

import java.util.List;

public class response {
    public record SignUpResult(
            Long userId,
            String name,
            String phone_number,
            String nickname
    ){}
    public record LoginResponse(
            String accessToken
    ) {
    }
    public record MyPageResponse(
            Long userId,
            String email,
            String nickname,
            String name
    ) {
    }
    public record HomeMissionSummary(
            Long missionId,
            String storeName,
            Integer reward,
            String missionContent,
            String status
    ) {}
    public record MissionPage(
            List<HomeMissionSummary> missionList,
            long totalCount,
            boolean firstPage,
            boolean lastPage,
            int totalPages,
            int pageSize
    ) {}
    public record HomeResponse(
            String regionName,
            Integer point,
            MissionPage missionpage
    ) {}
}
