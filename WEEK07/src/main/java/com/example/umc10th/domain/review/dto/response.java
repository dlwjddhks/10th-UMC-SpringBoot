package com.example.umc10th.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;

public class response {

    public record ReviewResult(
            Long reviewId,
            Integer rating,
            String content
    ) {}

    public record ReviewPreviewDTO(
            Long reviewId,
            Integer rating,
            String content,
            LocalDateTime createdAt
    ) {}

    public record ReviewListDTO(
            List<ReviewPreviewDTO> reviewList,
            Boolean hasNext,
            Long nextCursor
    ) {}
}