package com.example.umc10th.domain.review.dto;

import java.util.List;

public class request {

    public record CreateReviewDTO(
            Long userId,
            Long storeId,
            Long userMissionId,
            Integer rating,
            String content,
            Integer score
    ){}
}
