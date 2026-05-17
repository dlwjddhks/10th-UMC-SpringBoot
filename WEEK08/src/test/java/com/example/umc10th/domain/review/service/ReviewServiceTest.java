package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.review.dto.request;
import com.example.umc10th.domain.review.dto.response;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
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
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserMissionRepository userMissionRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Region region;
    private User user;
    private Store store;
    private Mission mission;
    private UserMission userMission;

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
                .name("맛있는 식당")
                .address("서울시 강남구 테헤란로 1")
                .status("ACTIVE")
                .build();

        mission = Mission.builder()
                .store(store)
                .name("10000원 이상 결제 미션")
                .description("10000원 이상 결제하면 포인트 지급")
                .missionConditionAmount(10000)
                .ownerVerificationCode("ABC123")
                .rewardPoint(200)
                .deadline(30)
                .build();

        userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status(Status.CHALLENGING)
                .build();
    }

    @Test
    @DisplayName("리뷰 생성 성공")
    void createReview_success() {
        request.CreateReviewDTO req = new request.CreateReviewDTO(1L, 1L, 1L, 4, "음식이 맛있어요", 100);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(userMissionRepository.findById(1L)).thenReturn(Optional.of(userMission));
        when(reviewRepository.save(any(Review.class))).thenAnswer(inv -> inv.getArgument(0));

        response.ReviewResult result = reviewService.createReview(req);

        assertThat(result.rating()).isEqualTo(4);
        assertThat(result.content()).isEqualTo("음식이 맛있어요");
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 생성 - 존재하지 않는 유저 → 예외 발생")
    void createReview_userNotFound_throwsException() {
        request.CreateReviewDTO req = new request.CreateReviewDTO(999L, 1L, 1L, 4, "맛있어요", 100);
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.createReview(req))
                .isInstanceOf(NoSuchElementException.class);

        verify(reviewRepository, never()).save(any());
    }

    @Test
    @DisplayName("리뷰 생성 - 존재하지 않는 가게 → 예외 발생")
    void createReview_storeNotFound_throwsException() {
        request.CreateReviewDTO req = new request.CreateReviewDTO(1L, 999L, 1L, 4, "맛있어요", 100);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.createReview(req))
                .isInstanceOf(NoSuchElementException.class);

        verify(reviewRepository, never()).save(any());
    }

    @Test
    @DisplayName("리뷰 생성 - 존재하지 않는 유저미션 → 예외 발생")
    void createReview_userMissionNotFound_throwsException() {
        request.CreateReviewDTO req = new request.CreateReviewDTO(1L, 1L, 999L, 4, "맛있어요", 100);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(userMissionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.createReview(req))
                .isInstanceOf(NoSuchElementException.class);

        verify(reviewRepository, never()).save(any());
    }

    @Test
    @DisplayName("내 리뷰 목록 조회 - 최신순, 커서 없음")
    void getMyReviews_sortByLatest_noCursor_success() {
        Long userId = 1L;
        int size = 2;

        List<Review> mockReviews = List.of(
                Review.builder().user(user).store(store).rating(4).content("리뷰1").build(),
                Review.builder().user(user).store(store).rating(3).content("리뷰2").build()
        );

        when(reviewRepository.findByUser_UserIdOrderByReviewIdDesc(eq(userId), any(Pageable.class)))
                .thenReturn(mockReviews);

        response.ReviewListDTO result = reviewService.getMyReviews(userId, null, size, "latest");

        assertThat(result.reviewList()).hasSize(2);
        assertThat(result.reviewList().get(0).content()).isEqualTo("리뷰1");
        assertThat(result.reviewList().get(1).content()).isEqualTo("리뷰2");
        assertThat(result.hasNext()).isTrue();
    }

    @Test
    @DisplayName("내 리뷰 목록 조회 - 최신순, 커서 있음")
    void getMyReviews_sortByLatest_withCursor_success() {
        Long userId = 1L;
        Long cursor = 10L;
        int size = 3;

        List<Review> mockReviews = List.of(
                Review.builder().user(user).store(store).rating(5).content("리뷰A").build()
        );

        when(reviewRepository.findByUser_UserIdAndReviewIdLessThanOrderByReviewIdDesc(
                eq(userId), eq(cursor), any(Pageable.class)))
                .thenReturn(mockReviews);

        response.ReviewListDTO result = reviewService.getMyReviews(userId, cursor, size, "latest");

        assertThat(result.reviewList()).hasSize(1);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.nextCursor()).isNull();
    }

    @Test
    @DisplayName("내 리뷰 목록 조회 - 평점순, 커서 없음")
    void getMyReviews_sortByRating_noCursor_success() {
        Long userId = 1L;
        int size = 2;

        List<Review> mockReviews = List.of(
                Review.builder().user(user).store(store).rating(5).content("최고에요").build(),
                Review.builder().user(user).store(store).rating(4).content("좋아요").build()
        );

        when(reviewRepository.findByUser_UserIdOrderByRatingDescReviewIdDesc(eq(userId), any(Pageable.class)))
                .thenReturn(mockReviews);

        response.ReviewListDTO result = reviewService.getMyReviews(userId, null, size, "rating");

        assertThat(result.reviewList()).hasSize(2);
        assertThat(result.reviewList().get(0).rating()).isEqualTo(5);
        assertThat(result.reviewList().get(1).rating()).isEqualTo(4);
        assertThat(result.hasNext()).isTrue();
    }

    @Test
    @DisplayName("내 리뷰 목록 조회 - 평점순, 커서 있음")
    void getMyReviews_sortByRating_withCursor_success() {
        Long userId = 1L;
        Long cursor = 5L;
        int size = 3;

        List<Review> mockReviews = List.of(
                Review.builder().user(user).store(store).rating(2).content("별로에요").build()
        );

        when(reviewRepository.findByUser_UserIdAndReviewIdLessThanOrderByRatingDescReviewIdDesc(
                eq(userId), eq(cursor), any(Pageable.class)))
                .thenReturn(mockReviews);

        response.ReviewListDTO result = reviewService.getMyReviews(userId, cursor, size, "rating");

        assertThat(result.reviewList()).hasSize(1);
        assertThat(result.hasNext()).isFalse();
        assertThat(result.nextCursor()).isNull();
    }

    @Test
    @DisplayName("내 리뷰 목록 조회 - 결과 없음")
    void getMyReviews_emptyResult() {
        Long userId = 1L;
        int size = 5;

        when(reviewRepository.findByUser_UserIdOrderByReviewIdDesc(eq(userId), any(Pageable.class)))
                .thenReturn(List.of());

        response.ReviewListDTO result = reviewService.getMyReviews(userId, null, size, "latest");

        assertThat(result.reviewList()).isEmpty();
        assertThat(result.hasNext()).isFalse();
        assertThat(result.nextCursor()).isNull();
    }
}
