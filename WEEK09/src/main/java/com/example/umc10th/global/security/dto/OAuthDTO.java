package com.example.umc10th.global.security.dto;

import com.example.umc10th.domain.user.enums.SocialType;

public interface OAuthDTO {
    SocialType getSocialType();
    String getSocialUid();
    String getSocialEmail();
    String getName();
}
