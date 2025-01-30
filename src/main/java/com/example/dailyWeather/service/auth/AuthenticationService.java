package com.example.dailyWeather.service.auth;

import com.example.dailyWeather.dto.AuthenticationDto;
import com.example.dailyWeather.dto.LoginDto;
import com.example.dailyWeather.entity.user.User;
import com.example.dailyWeather.exception.NotFoundException;
import com.example.dailyWeather.repository.UserRepository;
import com.example.dailyWeather.response.ResponseData;
import com.example.dailyWeather.security.JWTProvider;
import com.example.dailyWeather.util.MessageKey;
import com.example.dailyWeather.util.MessageService;
import com.example.dailyWeather.util.RestConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Override
    public ResponseData<AuthenticationDto> login(LoginDto loginDto) {
        // Foydalanuvchini autentifikatsiya qilish
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        // Foydalanuvchini topish
        User user = userRepository.findByUsernameAndDeletedFalse(loginDto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND)));

        // JWT tokenlarni generatsiya qilish
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        // Authentication DTO yaratish
        AuthenticationDto authenticationResponse = AuthenticationDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseData.successResponse(authenticationResponse);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        // Refresh token mavjudligini tekshirish
        if (authHeader == null || !authHeader.startsWith(RestConstant.TOKEN_TYPE)) {
            return;
        }

        refreshToken = authHeader.substring(7); // "Bearer "ni kesib tashlash
        username = jwtProvider.extractUsername(refreshToken);

        // Token validatsiya qilish
        if (username != null) {
            User user = userRepository.findByUsernameAndDeletedFalse(username).orElseThrow(
                    () -> new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND)));

            if (jwtProvider.isTokenValid(refreshToken, user)) {
                String accessToken = jwtProvider.generateAccessToken(user);
                String newRefreshToken = jwtProvider.generateRefreshToken(user);
                AuthenticationDto authenticationResponse = AuthenticationDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
        }
    }
}
