package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.Status;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.user.converter.HomeConverter;
import com.example.umc10th.domain.user.converter.UserConverter;
import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;

    public response.SignUpResult signUp(request.SignUpDTO request) {

        User user = User.builder()
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .nickname(request.nickname())
                .build();

        User savedUser = userRepository.save(user);

        return UserConverter.toSignUpResult(savedUser);
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