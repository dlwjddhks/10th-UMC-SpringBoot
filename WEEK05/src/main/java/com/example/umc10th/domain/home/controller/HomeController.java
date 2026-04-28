package com.example.umc10th.domain.home.controller;


import com.example.umc10th.domain.home.success.SuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/home")
    public ApiResponse<String> getHome() {

        return ApiResponse.onSuccess(
                SuccessCode.HOME_SUCCESS,
                null
        );
    }
}
