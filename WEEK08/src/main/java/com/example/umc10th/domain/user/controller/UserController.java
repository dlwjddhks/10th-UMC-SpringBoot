package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.service.UserService;
import com.example.umc10th.domain.user.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ApiResponse<response.SignUpResult> signUp(
            @RequestBody @Valid request.SignUpDTO request
    ){
        return ApiResponse.onSuccess(
                SuccessCode.USER_SIGNUP_SUCCESS,
                userService.signUp(request)
        );
    }
    @GetMapping("/home")

    public ApiResponse<response.HomeResponse> getHome(
            @RequestHeader("Authorization") String token,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ApiResponse.onSuccess(
                SuccessCode.HOME_SUCCESS,
                userService.getHome(token, page, size)
        );
    }
}
