package com.example.dailyWeather.mapper;

import com.example.dailyWeather.dto.UserDto;
import com.example.dailyWeather.dto.WeatherDto;
import com.example.dailyWeather.entity.user.User;
import com.example.dailyWeather.entity.weather.Weather;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<WeatherDto> toDto(List<Weather> weathers) {
        List<WeatherDto> weatherDto = new ArrayList<>();
        for (Weather weather : weathers) {
            weatherDto.add(toDto(weather));
        }
        return weatherDto;
    }

}
