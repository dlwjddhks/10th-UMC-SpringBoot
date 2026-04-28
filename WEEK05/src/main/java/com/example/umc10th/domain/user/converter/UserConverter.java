package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.entity.User;

public class UserConverter {
    public static response.SignUpResult toSignUpResult(
            Long userId,
            request.SignUpDTO request
    ) {
        User user = User.builder()
                .userId(userId)
                .name(request.name())
                .nickname(request.nickname())
                .build();

        return new response.SignUpResult(
                user.getUserId(),
                user.getName(),
                null,
                user.getNickname()

        );
    }
}
