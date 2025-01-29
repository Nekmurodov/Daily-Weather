package com.example.dailyWeather.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationDto  {
    private String accessToken;
    private String refreshToken;
}
