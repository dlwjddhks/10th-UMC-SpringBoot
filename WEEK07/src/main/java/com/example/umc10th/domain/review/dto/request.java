package com.example.umc10th.domain.review.dto;
import jakarta.validation.constraints.*;

import java.util.List;

public class request {


    public record CreateReviewDTO(

            @NotNull(message = "userId는 필수입니다.")
            Long userId,

            @NotNull(message = "storeId는 필수입니다.")
            Long storeId,

            @NotNull(message = "userMissionId는 필수입니다.")
            Long userMissionId,

            @NotNull(message = "평점은 필수입니다.")
            @Min(value = 1, message = "최소 1점 이상이어야 합니다.")
            @Max(value = 5, message = "최대 5점까지 가능합니다.")
            Integer rating,

            @NotBlank(message = "내용은 필수입니다.")
            String content,

            @NotNull(message = "score는 필수입니다.")
            Integer score
    ){}
}
