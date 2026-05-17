package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.request;
import com.example.umc10th.domain.review.dto.response;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.domain.review.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<Void> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid request.CreateReviewDTO req
    ) {

        return ApiResponse.onSuccess(
                SuccessCode.REVIEW_CREATE_SUCCESS,
                null
        );
    }
    private final ReviewService reviewService;

    @GetMapping("/users/{userId}/reviews")
    public ApiResponse<response.ReviewListDTO> getMyReviews(
            @PathVariable Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sort
    ) {

        return ApiResponse.onSuccess(
                SuccessCode.REVIEW_GET_SUCCESS,
                reviewService.getMyReviews(userId, cursor, size, sort)
        );
    }
}
