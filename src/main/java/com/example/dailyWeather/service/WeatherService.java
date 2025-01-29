package com.example.dailyWeather.service;

import com.example.dailyWeather.entity.weather.Weather;
import com.example.dailyWeather.exception.NotFoundException;
import com.example.dailyWeather.feingClient.WeatherClient;
import com.example.dailyWeather.mapper.WeatherMapper;
import com.example.dailyWeather.repository.WeatherRepository;
import com.example.dailyWeather.response.ResponseData;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Value("${weather.api-key}")
    private String apiKey;

    public void getWeather(String city) {
        JsonNode body = weatherClient.getWeather(apiKey, city);

        Weather weather = new Weather();
        weather.setName(body.get("location").get("name").asText());
        weather.setCountry(body.get("location").get("country").asText());
        weather.setLat(body.get("location").get("lat").asDouble());
        weather.setLon(body.get("location").get("lon").asDouble());
        weather.setTempC(body.get("current").get("temp_c").asDouble());
        weather.setWindKph(body.get("current").get("wind_kph").asDouble());
        weather.setCloud(body.get("current").get("cloud").asInt());
        weather.setTempColor(getTempColor(weather.getTempC()));
        weather.setWindColor(getWindColor(weather.getWindKph()));
        weather.setCloudColor(getCloudColor(weather.getCloud()));
        weather.setRecordedAt(LocalDateTime.now());

        weatherRepository.save(weather);
    }

    private String getTempColor(double tempC) {
        if (tempC <= -30) return "#003366";
        if (tempC <= -20) return "#4A90E2";
        if (tempC <= -10) return "#B3DFFD";
        if (tempC <= 0) return "#E6F7FF";
        if (tempC <= 10) return "#D1F2D3";
        if (tempC <= 20) return "#FFFACD";
        if (tempC <= 30) return "#FFCC80";
        if (tempC <= 40) return "#FF7043";
        return "#D32F2F";
    }

    private String getWindColor(double windKph) {
        if (windKph <= 10) return "#E0F7FA";
        if (windKph <= 20) return "#B2EBF2";
        if (windKph <= 40) return "#4DD0E1";
        if (windKph <= 60) return "#0288D1";
        return "#01579B";
    }

    private String getCloudColor(int cloud) {
        if (cloud <= 10) return "#FFF9C4";
        if (cloud <= 30) return "#FFF176";
        if (cloud <= 60) return "#E0E0E0";
        if (cloud <= 90) return "#9E9E9E";
        return "#616161";
    }

    public ResponseData<?> getName(String name) {
        Optional<Weather> optionalWeather = this.weatherRepository.findByName(name);
        if (optionalWeather.isEmpty()) {
            throw new NotFoundException("There is no weather information available for this city.");
        }
        return ResponseData.successResponse(this.weatherMapper.toDto(optionalWeather.get()));
    }
}
