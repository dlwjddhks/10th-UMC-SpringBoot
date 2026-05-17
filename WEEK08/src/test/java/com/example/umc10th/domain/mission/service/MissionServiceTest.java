package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.response;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    @Mock
    private UserMissionRepository userMissionRepository;

    @InjectMocks
    private MissionService missionService;

    private Region region;
    private User user;
    private Store store;
    private Mission mission;

    @BeforeEach
    void setUp() {
        region = Region.builder()
                .name("서울")
                .build();

        user = User.builder()
                .email("test@test.com")
                .password("password1234")
                .name("홍길동")
                .nickname("길동이")
                .gender(Gender.MALE)
                .birth(LocalDate.of(2000, 1, 1))
                .region(region)
                .status("ACTIVE")
                .build();

        store = Store.builder()
                .region(region)
                .name("테스트 가게")
                .address("서울시 강남구 1번지")
                .status("ACTIVE")
                .build();

        mission = Mission.builder()
                .store(store)
                .name("스탬프 미션")
                .description("3회 방문 시 포인트 지급")
                .missionConditionAmount(5000)
                .ownerVerificationCode("XYZ789")
                .rewardPoint(500)
                .deadline(60)
                .build();
    }

    @Test
    @DisplayName("진행 중인 미션 목록 조회 - 단일 결과")
    void getMyMissions_returnsSingleResult() {
        Long userId = 1L;
        int page = 0;

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status(Status.CHALLENGING)
                .build();

        Page<UserMission> mockPage = new PageImpl<>(
                List.of(userMission),
                PageRequest.of(page, 10),
                1
        );

        when(userMissionRepository.findByUserUserIdAndStatusIn(
                eq(userId), anyList(), any(Pageable.class)))
                .thenReturn(mockPage);

        response.MissionPageDTO result = missionService.getMyMissions(userId, page);

        assertThat(result.missionList()).hasSize(1);
        assertThat(result.missionList().get(0).name()).isEqualTo("스탬프 미션");
        assertThat(result.missionList().get(0).status()).isEqualTo(Status.CHALLENGING);
        assertThat(result.totalCount()).isEqualTo(1);
        assertThat(result.firstPage()).isTrue();
        assertThat(result.lastPage()).isTrue();
        assertThat(result.totalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("진행 중인 미션 목록 조회 - 결과 없음")
    void getMyMissions_emptyResult() {
        Long userId = 2L;
        int page = 0;

        Page<UserMission> emptyPage = new PageImpl<>(
                List.of(),
                PageRequest.of(page, 10),
                0
        );

        when(userMissionRepository.findByUserUserIdAndStatusIn(
                eq(userId), anyList(), any(Pageable.class)))
                .thenReturn(emptyPage);

        response.MissionPageDTO result = missionService.getMyMissions(userId, page);

        assertThat(result.missionList()).isEmpty();
        assertThat(result.totalCount()).isEqualTo(0);
        assertThat(result.firstPage()).isTrue();
        assertThat(result.lastPage()).isTrue();
        assertThat(result.totalPages()).isEqualTo(0);
        assertThat(result.pageSize()).isEqualTo(0);
    }

    @Test
    @DisplayName("진행 중인 미션 목록 조회 - 페이지 크기(10) 초과 데이터")
    void getMyMissions_multiplePages() {
        Long userId = 1L;
        int page = 0;
        int totalElements = 25;

        List<UserMission> pageContent = buildUserMissions(10);

        Page<UserMission> mockPage = new PageImpl<>(
                pageContent,
                PageRequest.of(page, 10),
                totalElements
        );

        when(userMissionRepository.findByUserUserIdAndStatusIn(
                eq(userId), anyList(), any(Pageable.class)))
                .thenReturn(mockPage);

        response.MissionPageDTO result = missionService.getMyMissions(userId, page);

        assertThat(result.missionList()).hasSize(10);
        assertThat(result.totalCount()).isEqualTo(25);
        assertThat(result.firstPage()).isTrue();
        assertThat(result.lastPage()).isFalse();
        assertThat(result.totalPages()).isEqualTo(3);
        assertThat(result.pageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("진행 중인 미션 목록 조회 - 마지막 페이지")
    void getMyMissions_lastPage() {
        Long userId = 1L;
        int page = 2;
        int totalElements = 25;

        List<UserMission> pageContent = buildUserMissions(5);

        Page<UserMission> mockPage = new PageImpl<>(
                pageContent,
                PageRequest.of(page, 10),
                totalElements
        );

        when(userMissionRepository.findByUserUserIdAndStatusIn(
                eq(userId), anyList(), any(Pageable.class)))
                .thenReturn(mockPage);

        response.MissionPageDTO result = missionService.getMyMissions(userId, page);

        assertThat(result.missionList()).hasSize(5);
        assertThat(result.firstPage()).isFalse();
        assertThat(result.lastPage()).isTrue();
    }

    private List<UserMission> buildUserMissions(int count) {
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(i -> UserMission.builder()
                        .user(user)
                        .mission(mission)
                        .status(Status.CHALLENGING)
                        .build())
                .toList();
    }
}
