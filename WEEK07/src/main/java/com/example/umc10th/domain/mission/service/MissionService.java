package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final UserMissionRepository userMissionRepository;

    public response.MissionPageDTO getMyMissions(Long userId, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10);

        Page<UserMission> result = userMissionRepository
                .findByUserUserIdAndStatusIn(
                        userId,
                        List.of(Status.CHALLENGING),
                        pageRequest
                );
        return MissionConverter.toMissionPageDTO(result);
    }
}