package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.mission.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    private String title;

    private Integer reward;

    private Status status; // 일단 문자열 (나중에 Enum 추천)
}