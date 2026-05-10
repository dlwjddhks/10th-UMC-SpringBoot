package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.review.dto.request;
import com.example.umc10th.domain.review.dto.response;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final UserMissionRepository userMissionRepository;
    private final ReviewRepository reviewRepository;
    @Transactional
    public response.ReviewResult createReview(request.CreateReviewDTO req) {

        User user = userRepository.findById(req.userId()).orElseThrow();
        Store store = storeRepository.findById(req.storeId()).orElseThrow();
        UserMission userMission = userMissionRepository.findById(req.userMissionId()).orElseThrow();

        Review review = Review.builder()
                .user(user)
                .store(store)
                .userMission(userMission)
                .content(req.content())
                .rating(req.rating())
                .build();

        reviewRepository.save(review);

        return new response.ReviewResult(
                review.getReviewId(),
                review.getRating(),
                review.getContent()
        );
    }
    @Transactional
    public response.ReviewListDTO getMyReviews(
            Long userId,
            Long cursor,
            Integer size,
            String sort
    ) {

        Pageable pageable = PageRequest.of(0, size);

        List<Review> reviews;

        if (sort.equals("rating")) {

            if (cursor == null) {
                reviews = reviewRepository
                        .findByUser_UserIdOrderByRatingDescReviewIdDesc(
                                userId,
                                pageable
                        );
            } else {
                reviews = reviewRepository
                        .findByUser_UserIdAndReviewIdLessThanOrderByRatingDescReviewIdDesc(
                                userId,
                                cursor,
                                pageable
                        );
            }

        } else {

            if (cursor == null) {
                reviews = reviewRepository
                        .findByUser_UserIdOrderByReviewIdDesc(
                                userId,
                                pageable
                        );
            } else {
                reviews = reviewRepository
                        .findByUser_UserIdAndReviewIdLessThanOrderByReviewIdDesc(
                                userId,
                                cursor,
                                pageable
                        );
            }
        }

        List<response.ReviewPreviewDTO> reviewList =
                reviews.stream()
                        .map(review -> new response.ReviewPreviewDTO(
                                review.getReviewId(),
                                review.getRating(),
                                review.getContent(),
                                review.getCreatedAt()
                        ))
                        .toList();

        boolean hasNext = reviews.size() == size;

        Long nextCursor = hasNext
                ? reviews.get(reviews.size() - 1).getReviewId()
                : null;

        return new response.ReviewListDTO(
                reviewList,
                hasNext,
                nextCursor
        );
    }
}
