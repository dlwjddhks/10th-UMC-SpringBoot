package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.entity.User;

public class UserConverter {
    public static response.SignUpResult toSignUpResult(User user) {

        return new response.SignUpResult(
                user.getUserId(),
                user.getName(),
                null,
                user.getNickname()

        );
    }
}
