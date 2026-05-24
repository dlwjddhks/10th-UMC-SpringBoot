package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.repository.RegionRepository;
import com.example.umc10th.domain.user.converter.HomeConverter;
import com.example.umc10th.domain.user.converter.UserConverter;
import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.enums.UserErrorCode;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.repository.UserRepository;
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public response.SignUpResult signUp(request.SignUpDTO request) {
        if (userRepository.existsByEmail(request.email())){
            throw new UserException(UserErrorCode.USER_ALREADY_EXISTS);
        }
        Region region = regionRepository.findById(request.regionId())
                .orElseThrow();
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .name(request.name())
                .gender(request.gender())
                .birth(request.birth())
                .region(region)
                .status(com.example.umc10th.domain.user.enums.Status.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        return UserConverter.toSignUpResult(savedUser);
    }
    public response.LoginResponse login(
            request.LoginDTO request
    ) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(
                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                );
        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new UserException(
                    UserErrorCode.INVALID_PASSWORD
            );
        }
        AuthMember authMember =
                new AuthMember(user);
        String accessToken =
                jwtUtil.createAccessToken(authMember);
        return new response.LoginResponse(accessToken);
    }
    public response.MyPageResponse getMyPage(
            AuthMember member
    ) {
        return UserConverter.toMyPageResponse(
                member.getUser()
        );
    }
    public response.HomeResponse getHome(String token, int page, int size) {

        Long userId = 1L;

        User user = userRepository.findById(userId)
                .orElseThrow();

        Pageable pageable = PageRequest.of(page, size);

        Page<UserMission> missions =
                userMissionRepository.findByUserUserIdAndStatusIn(
                        userId,
                        List.of(Status.CHALLENGING),
                        pageable
                );

        return HomeConverter.toHomeResponse(user, missions);
    }
}