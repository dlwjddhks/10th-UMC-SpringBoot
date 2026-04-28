package com.example.umc10th.domain.user.entity;

import com.example.umc10th.domain.user.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    private String password;
    private String name;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birth;

}
