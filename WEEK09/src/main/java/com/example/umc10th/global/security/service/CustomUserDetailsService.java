package com.example.umc10th.global.security.service;

import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.enums.SocialType;
import com.example.umc10th.domain.user.enums.UserErrorCode;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.repository.UserRepository;
import com.example.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService{

    private final UserRepository userRepository;

    public UserDetails loadUserByUidAndSocialType(
            SocialType socialType,
            String username
    ) throws UsernameNotFoundException {
        User user = userRepository.findBySocialTypeAndSocialId(socialType.name(), username)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return new AuthMember(user);
    }
}
