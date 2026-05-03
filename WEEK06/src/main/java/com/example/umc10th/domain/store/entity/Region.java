package com.example.umc10th.domain.store.entity;

import com.example.umc10th.domain.user.entity.User;
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
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "region")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "region")
    private List<Store> stores = new ArrayList<>();
}
