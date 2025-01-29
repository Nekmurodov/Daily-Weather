package com.example.dailyWeather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
    private String name;
    private String country;
    private double lat;
    private double lon;
    private double tempC;
    private String tempColor;
    private double windKph;
    private String windColor;
    private int cloud;
    private String cloudColor;

    private LocalDateTime recordedAt;
}
