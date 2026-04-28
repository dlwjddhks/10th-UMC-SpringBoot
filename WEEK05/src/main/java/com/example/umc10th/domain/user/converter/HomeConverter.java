package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.response;

import java.util.List;

public class HomeConverter {

    public static response.HomeResponse toHomeResponse(
            Long userId,
            String nickname,
            List<response.HomeMissionSummary> missions
    ) {
        return new response.HomeResponse(
                userId,
                nickname,
                missions
        );
    }
}
