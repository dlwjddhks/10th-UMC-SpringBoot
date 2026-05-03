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
import org.springframework.stereotype.Service;

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
}
