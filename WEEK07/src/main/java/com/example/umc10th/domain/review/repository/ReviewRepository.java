package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser_UserIdAndReviewIdLessThanOrderByReviewIdDesc(
            Long userId,
            Long cursor,
            Pageable pageable
    );

    List<Review> findByUser_UserIdOrderByReviewIdDesc(
            Long userId,
            Pageable pageable
    );
    List<Review> findByUser_UserIdOrderByRatingDescReviewIdDesc(
            Long userId,
            Pageable pageable
    );

    List<Review> findByUser_UserIdAndReviewIdLessThanOrderByRatingDescReviewIdDesc(
            Long userId,
            Long cursor,
            Pageable pageable
    );
}