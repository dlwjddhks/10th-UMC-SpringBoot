package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.request;
import com.example.umc10th.domain.review.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<Void> createReview(
            @PathVariable Long storeId,
            @RequestBody request.CreateReviewDTO req
    ) {

        return ApiResponse.onSuccess(
                SuccessCode.REVIEW_CREATE_SUCCESS,
                null
        );
    }
}
