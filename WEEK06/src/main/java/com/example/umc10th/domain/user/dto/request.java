package com.example.umc10th.domain.user.dto;

import lombok.Getter;

import java.util.List;

public class request {

    public record SignUpDTO(
        String email,
        String password,
        String nickname,
        String name,
        String gender,
        String birth,
        Long regionId,
        List<AgreementDTO> agreements,
        List<Long> preferredFoodCategoryIds
    ){}
    public record AgreementDTO(
            Long agreementTypeId,
            Boolean isAgreed
    ){}
}
