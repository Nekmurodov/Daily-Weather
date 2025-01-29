package com.example.dailyWeather.controller;

import com.example.dailyWeather.dto.LoginDto;
import com.example.dailyWeather.dto.RegistrationRequest;
import com.example.dailyWeather.response.ResponseData;
import com.example.dailyWeather.service.UserService;
import com.example.dailyWeather.service.email.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseData<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return this.userService.registerUser(registrationRequest);
    }

    @PostMapping("/login")
    public ResponseData<?> loginUser(@RequestBody LoginDto loginDto) {
        return this.authenticationService.login(loginDto);
    }

}
