package com.example.umc10th.domain.user.dto;

public class response {
    public record SignUpResult(
            Long userId,
            String nickname
    ){}
}
