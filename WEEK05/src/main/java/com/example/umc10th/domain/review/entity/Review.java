package com.example.umc10th.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long userId;
    private Long storeId;

    private Long userMissionId;

    private Integer rating;
    private String content;
}