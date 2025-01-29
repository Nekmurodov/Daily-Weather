package com.example.dailyWeather.entity.weather;

import com.example.dailyWeather.entity.AbsEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity()
public class Weather extends AbsEntity {

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
