package com.example.umc10th.domain.store.entity;

import com.example.umc10th.domain.user.entity.UserPreferenceFood;
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
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodCategoryId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "foodCategory")
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "foodCategory")
    private List<UserPreferenceFood> userPreferences = new ArrayList<>();

}
