package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer missionConditionAmount;

    @Column(nullable = false)
    private String ownerVerificationCode;

    @Column(nullable = false)
    private Integer rewardPoint;

    @Column(nullable = false)
    private Integer deadline;

    @OneToMany(mappedBy = "mission")
    private List<UserMission> userMissions = new ArrayList<>();
}