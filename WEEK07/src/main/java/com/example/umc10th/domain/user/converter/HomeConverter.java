package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class HomeConverter {

    public static response.HomeResponse toHomeResponse(
            User user,
            Page<UserMission> page
    ) {
        return new response.HomeResponse(
                user.getRegion().getName(),   // regionName
                0,                            // point (나중에 wallet 연결)
                toMissionPage(page)
        );
    }

    private static response.MissionPage toMissionPage(
            Page<UserMission> page
    ) {

        List<response.HomeMissionSummary> missions =
                page.getContent().stream()
                        .map(HomeConverter::toMissionSummary)
                        .toList();

        return new response.MissionPage(
                missions,                      // missionList
                page.getTotalElements(),       // totalCount
                page.isFirst(),                // firstPage
                page.isLast(),                 // lastPage
                page.getTotalPages(),          // totalPages
                page.getNumberOfElements()     // pageSize
        );
    }

    private static response.HomeMissionSummary toMissionSummary(
            UserMission m
    ) {
        return new response.HomeMissionSummary(
                m.getMission().getMissionId(),
                m.getMission().getStore().getName(),
                m.getMission().getRewardPoint(),
                m.getMission().getName(),
                m.getStatus().name()
        );
    }
}