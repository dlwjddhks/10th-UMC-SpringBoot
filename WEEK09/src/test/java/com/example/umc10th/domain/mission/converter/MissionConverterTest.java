package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MissionConverterTest {

    private Region region;
    private User user;
    private Store store;
    private Mission mission;

    @BeforeEach
    void setUp() {
        region = Region.builder()
                .name("부산")
                .build();

        user = User.builder()
                .email("converter@test.com")
                .password("pass1234")
                .name("김테스트")
                .nickname("테스트닉")
                .gender(Gender.FEMALE)
                .birth(LocalDate.of(1995, 6, 15))
                .region(region)
                .status("ACTIVE")
                .build();

        store = Store.builder()
                .region(region)
                .name("컨버터 가게")
                .address("부산시 해운대구 1번지")
                .status("ACTIVE")
                .build();

        mission = Mission.builder()
                .store(store)
                .name("방문 미션")
                .description("가게 방문 시 포인트 지급")
                .missionConditionAmount(3000)
                .ownerVerificationCode("CONV999")
                .rewardPoint(150)
                .deadline(7)
                .build();
    }

    @Test
    @DisplayName("toMissionDTO - UserMission을 MissionListResDTO로 올바르게 변환")
    void toMissionDTO_correctMapping() {
        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status(Status.CHALLENGING)
                .build();

        response.MissionListResDTO dto = MissionConverter.toMissionDTO(userMission);

        assertThat(dto.name()).isEqualTo("방문 미션");
        assertThat(dto.status()).isEqualTo(Status.CHALLENGING);
    }

    @Test
    @DisplayName("toMissionDTO - COMPLETE 상태도 올바르게 변환")
    void toMissionDTO_completeStatus() {
        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status(Status.COMPLETE)
                .build();

        response.MissionListResDTO dto = MissionConverter.toMissionDTO(userMission);

        assertThat(dto.status()).isEqualTo(Status.COMPLETE);
    }

    @Test
    @DisplayName("toMissionPageDTO - 단일 항목 페이지 올바르게 변환")
    void toMissionPageDTO_singleItem() {
        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status(Status.CHALLENGING)
                .build();

        Page<UserMission> page = new PageImpl<>(
                List.of(userMission),
                PageRequest.of(0, 10),
                1
        );

        response.MissionPageDTO result = MissionConverter.toMissionPageDTO(page);

        assertThat(result.missionList()).hasSize(1);
        assertThat(result.missionList().get(0).name()).isEqualTo("방문 미션");
        assertThat(result.totalCount()).isEqualTo(1);
        assertThat(result.firstPage()).isTrue();
        assertThat(result.lastPage()).isTrue();
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.pageSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("toMissionPageDTO - 빈 페이지 변환")
    void toMissionPageDTO_emptyPage() {
        Page<UserMission> emptyPage = new PageImpl<>(
                List.of(),
                PageRequest.of(0, 10),
                0
        );

        response.MissionPageDTO result = MissionConverter.toMissionPageDTO(emptyPage);

        assertThat(result.missionList()).isEmpty();
        assertThat(result.totalCount()).isEqualTo(0);
        assertThat(result.totalPages()).isEqualTo(0);
        assertThat(result.pageSize()).isEqualTo(0);
    }

    @Test
    @DisplayName("toMissionPageDTO - 다중 항목, 멀티 페이지 정보 올바르게 반영")
    void toMissionPageDTO_multipleItems_multiPage() {
        List<UserMission> items = List.of(
                UserMission.builder().user(user).mission(mission).status(Status.CHALLENGING).build(),
                UserMission.builder().user(user).mission(mission).status(Status.CHALLENGING).build(),
                UserMission.builder().user(user).mission(mission).status(Status.CHALLENGING).build()
        );

        Page<UserMission> page = new PageImpl<>(
                items,
                PageRequest.of(0, 3),
                9
        );

        response.MissionPageDTO result = MissionConverter.toMissionPageDTO(page);

        assertThat(result.missionList()).hasSize(3);
        assertThat(result.totalCount()).isEqualTo(9);
        assertThat(result.firstPage()).isTrue();
        assertThat(result.lastPage()).isFalse();
        assertThat(result.totalPages()).isEqualTo(3);
        assertThat(result.pageSize()).isEqualTo(3);
    }
}
