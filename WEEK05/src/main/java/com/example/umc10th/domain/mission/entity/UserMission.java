package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.mission.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMissionId;

    private Long userId;
    private Long missionId;

    private Status status; // CHALLENGING / COMPLETE
}