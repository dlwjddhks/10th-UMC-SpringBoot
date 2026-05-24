package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.request;
import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreErrorCode;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;
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

    @Transactional
    public Void createMission(
            Long storeId,
            request.CreateMission dto
    ){
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        Mission mission = MissionConverter.toMission(store,dto);

        missionRepository.save(mission);
        return null;
    }
    public List<response.GetMission> getMissions(Long storeId) {

        List<Mission> missionList = missionRepository.findAllByStore_StoreId(storeId);

        return missionList.stream()

                .map(MissionConverter::toGetMission)

                .toList();

    }
}