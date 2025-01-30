package com.example.dailyWeather.service;

import com.example.dailyWeather.entity.weather.Weather;
import com.example.dailyWeather.exception.NotFoundException;
import com.example.dailyWeather.feingClient.WeatherClient;
import com.example.dailyWeather.mapper.WeatherMapper;
import com.example.dailyWeather.repository.WeatherRepository;
import com.example.dailyWeather.response.ResponseData;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;


    public ResponseData<?> getName(String name) {
        Optional<Weather> optionalWeather = this.weatherRepository.findByName(name);
        return optionalWeather.map(weather -> ResponseData.successResponse(this.weatherMapper.toDto(weather)))
                .orElseGet(() -> ResponseData.successResponse(this.weatherMapper.toDto(getWeather(name))));
    }

//    public ResponseData<?> getAllCityName() {
//        List<String> list =this.weatherRepository.findAllNames();
//        if (list.isEmpty()){
//            throw new NotFoundException("City names not found");
//        }
//        return ResponseData.successResponse();
//    }


    public ResponseData<?> getByNames(List<String> names) {
        List<Weather> weatherList = names.stream()
                .map(name -> weatherRepository.findByName(name).orElseGet(() -> getWeather(name)))
                .toList();
        return ResponseData.successResponse(this.weatherMapper.toDto(weatherList));
    }

    public ResponseData<?> getById(UUID cityId) {
        Weather weather = weatherRepository.findById(cityId)
                .orElseThrow(() -> new NotFoundException("City not found"));
        return ResponseData.successResponse(weatherMapper.toDto(weather));
    }

    public ResponseData<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Weather> weathers = this.weatherRepository.findAll(pageable);
        if (weathers.getTotalElements() == 0) {
            throw new NotFoundException("Weathers not found");
        }
        return ResponseData.successResponse(
                Map.of(
                        "data", weatherMapper.toDto(weathers.getContent()),
                        "totalPages", weathers.getTotalPages(),
                        "total", weathers.getTotalElements()
                )
        );
    }

    public Weather getWeather(String city) {
        JsonNode body = weatherClient.getWeather( city);
        Weather weather = weatherRepository.findByName(city).orElseGet(Weather::new);

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
        return weather;
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

}
