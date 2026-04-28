package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @PostMapping("/signup")
    public ApiResponse<response.SignUpResult> signUp(
            @RequestBody request.SignUpDTO request
    ){
        return ApiResponse.onSuccess(
                SuccessCode.USER_SIGNUP_SUCCESS,
                null
        );
    }
    @GetMapping("/home")
    public ApiResponse<response.HomeResponse> getHome(
            @RequestHeader("Authorization") String token
    ) {

        // 아직 Service 없으니까 값 없이 구조만 유지
        response.HomeResponse result = new response.HomeResponse(
                null,       // userId
                null,       // nickname
                null        // missions
        );

        return ApiResponse.onSuccess(
                SuccessCode.HOME_SUCCESS,
                result
        );
    }

}
