package com.example.dailyWeather.controller;

import com.example.dailyWeather.dto.LoginDto;
import com.example.dailyWeather.dto.RegistrationRequest;
import com.example.dailyWeather.response.ResponseData;
import com.example.dailyWeather.service.UserService;
import com.example.dailyWeather.service.auth.AuthenticationService;
import com.example.dailyWeather.util.RestConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping(RestConstant.AUTH_API)
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseData<?> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return this.userService.registerUser(registrationRequest);
    }

    @PostMapping("/login")
    public ResponseData<?> loginUser(@RequestBody LoginDto loginDto) {
        return this.authenticationService.login(loginDto);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.authenticationService.refreshToken(request, response);
    }

}
