package com.example.umc10th.domain.user.entity;

import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.user.enums.Gender;
import com.example.umc10th.domain.user.enums.Status;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birth;

    private String phoneNum;
    private String profileImage;

    @Builder.Default
    @Column(nullable = false)
    private Boolean emailCertification = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean phoneCertification = false;

    private String socialType;
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "user")
    private List<UserMission> userMissions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
}