package com.example.dailyWeather.mapper;

import com.example.dailyWeather.dto.WeatherDto;
import com.example.dailyWeather.entity.weather.Weather;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public WeatherDto toDto(Weather weather) {
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setName(weather.getName());
        weatherDto.setCountry(weather.getCountry());
        weatherDto.setLon(weather.getLon());
        weatherDto.setLat(weather.getLat());
        weatherDto.setCloud(weather.getCloud());
        weatherDto.setCloudColor(weather.getCloudColor());
        weatherDto.setTempC(weather.getTempC());
        weatherDto.setTempColor(weather.getTempColor());
        weatherDto.setWindKph(weather.getWindKph());
        weatherDto.setWindColor(weather.getWindColor());
        weatherDto.setRecordedAt(weather.getRecordedAt());
        return weatherDto;
    }

}
