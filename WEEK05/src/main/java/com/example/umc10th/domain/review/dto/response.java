package com.example.umc10th.domain.review.dto;

public class response {

    public record ReviewResult(
            Long reviewId,
            Integer rating,
            String content
    ) {}
}