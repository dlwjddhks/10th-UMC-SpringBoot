package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.RegionRepository;
import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.enums.Gender;
import com.example.umc10th.domain.user.repository.UserRepository;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMissionRepository userMissionRepository;
    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private UserService userService;

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
                .name("포인트 미션")
                .description("결제 시 포인트 적립")
                .missionConditionAmount(8000)
                .ownerVerificationCode("MISSION01")
                .rewardPoint(300)
                .deadline(14)
                .build();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() {
        request.SignUpDTO req = new request.SignUpDTO(
                "test@test.com",
                "password1234",
                "길동이",
                "홍길동",
                Gender.MALE,
                LocalDate.of(2000, 1, 1),
                1L,
                List.of(),
                List.of()
        );

        when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
        when(userRepository.save(any(User.class))).thenReturn(user);

        response.SignUpResult result = userService.signUp(req);

        assertThat(result.name()).isEqualTo("홍길동");
        assertThat(result.nickname()).isEqualTo("길동이");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 - 존재하지 않는 지역 → 예외 발생")
    void signUp_regionNotFound_throwsException() {
        request.SignUpDTO req = new request.SignUpDTO(
                "test@test.com",
                "password1234",
                "길동이",
                "홍길동",
                Gender.MALE,
                LocalDate.of(2000, 1, 1),
                999L,
                List.of(),
                List.of()
        );

        when(regionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.signUp(req))
                .isInstanceOf(NoSuchElementException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("홈 화면 조회 성공 - 진행 중인 미션 있음")
    void getHome_withMissions_success() {
        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status(Status.CHALLENGING)
                .build();

        Page<UserMission> missionPage = new PageImpl<>(
                List.of(userMission),
                PageRequest.of(0, 10),
                1
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMissionRepository.findByUserUserIdAndStatusIn(
                eq(1L), anyList(), any(Pageable.class)))
                .thenReturn(missionPage);

        response.HomeResponse result = userService.getHome("dummy-token", 0, 10);

        assertThat(result.regionName()).isEqualTo("서울");
        assertThat(result.point()).isEqualTo(0);
        assertThat(result.missionpage().missionList()).hasSize(1);
        assertThat(result.missionpage().missionList().get(0).missionContent()).isEqualTo("포인트 미션");
        assertThat(result.missionpage().missionList().get(0).storeName()).isEqualTo("테스트 가게");
        assertThat(result.missionpage().missionList().get(0).reward()).isEqualTo(300);
        assertThat(result.missionpage().totalCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("홈 화면 조회 성공 - 진행 중인 미션 없음")
    void getHome_noMissions_success() {
        Page<UserMission> emptyPage = new PageImpl<>(
                List.of(),
                PageRequest.of(0, 10),
                0
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMissionRepository.findByUserUserIdAndStatusIn(
                eq(1L), anyList(), any(Pageable.class)))
                .thenReturn(emptyPage);

        response.HomeResponse result = userService.getHome("dummy-token", 0, 10);

        assertThat(result.regionName()).isEqualTo("서울");
        assertThat(result.missionpage().missionList()).isEmpty();
        assertThat(result.missionpage().totalCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("홈 화면 조회 - 유저 없음 → 예외 발생")
    void getHome_userNotFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getHome("dummy-token", 0, 10))
                .isInstanceOf(NoSuchElementException.class);
    }
}
