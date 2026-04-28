package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.request;
import com.example.umc10th.domain.user.dto.response;
import com.example.umc10th.domain.user.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
