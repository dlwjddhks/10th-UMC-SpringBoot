package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.global.security.dto.OAuthDTO;

public class UserConverter {

    public static response.SignUpResult toSignUpResult(User user) {

        return new response.SignUpResult(
                user.getUserId(),
                user.getName(),
                null,
                user.getNickname()

        );
    }
    public static response.MyPageResponse toMyPageResponse(
            User user
    ) {
        return new response.MyPageResponse(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getName()
        );
    }
    public static response.LoginResponse toLogin(
            String accessToken
    ) {
        return new response.LoginResponse(accessToken);
    }
    public static User toUser(OAuthDTO dto) {
        return User.builder()
                .email(dto.getSocialEmail())
                .nickname(dto.getName())
                .name(dto.getName())
                .socialId(dto.getSocialUid())
                .socialType(dto.getSocialType().name())
                .build();
    }
}
