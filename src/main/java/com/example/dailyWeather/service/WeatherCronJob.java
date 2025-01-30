package com.example.dailyWeather.service;

import com.example.dailyWeather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherCronJob {

    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Har kuni soat 00:00 da ishlaydi
    public void updateWeatherData() {
        List<String> countries = this.weatherRepository.findAllNames();
        countries.forEach(weatherService::getWeather);
    }

}
