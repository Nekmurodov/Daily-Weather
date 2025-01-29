package com.example.dailyWeather.service.email;

import com.example.dailyWeather.dto.AuthenticationDto;
import com.example.dailyWeather.dto.LoginDto;
import com.example.dailyWeather.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthenticationService {

    ResponseData<AuthenticationDto> login(LoginDto loginDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
