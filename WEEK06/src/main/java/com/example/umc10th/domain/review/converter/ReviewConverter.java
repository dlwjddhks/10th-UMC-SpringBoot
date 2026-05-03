package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.dto.response;

public class ReviewConverter {

    public static response.ReviewResult toReviewResult(Review review) {
        return new response.ReviewResult(
                review.getReviewId(),
                review.getRating(),
                review.getContent()
        );
    }
}