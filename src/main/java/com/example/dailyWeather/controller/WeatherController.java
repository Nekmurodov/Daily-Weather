package com.example.dailyWeather.controller;

import com.example.dailyWeather.response.ResponseData;
import com.example.dailyWeather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/weather/")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/get")
    public ResponseData<?> get(@RequestParam String name) {
        return this.weatherService.getName(name);
    }

}
