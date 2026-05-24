package com.example.umc10th.domain.user.dto;

import com.example.umc10th.domain.user.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class request {
    public record LoginDTO(

            String email,
            String password

    ) {

    }
    public record SignUpDTO(
            @NotBlank(message = "이메일은 필수입니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            String password,

            @NotBlank(message = "닉네임은 필수입니다.")
            String nickname,

            @NotBlank(message = "이름은 필수입니다.")
            String name,

            Gender gender,

            LocalDate birth,

            Long regionId,

            List<AgreementDTO> agreements,

            List<Long> preferredFoodCategoryIds
    ) {}
    public record AgreementDTO(
            Long agreementTypeId,
            Boolean isAgreed
    ){}
}
