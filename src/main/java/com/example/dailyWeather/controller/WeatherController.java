package com.example.dailyWeather.controller;

import com.example.dailyWeather.response.ResponseData;
import com.example.dailyWeather.service.WeatherService;
import com.example.dailyWeather.valid.CheckRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/weather/")
public class WeatherController {

    private final WeatherService weatherService;

    @CheckRole("USER,ADMIN")
    @GetMapping("/get/{cityId}")
    public ResponseData<?> getById(@PathVariable UUID cityId) {
        return this.weatherService.getById(cityId);
    }

    @CheckRole("USER,ADMIN")
    @GetMapping("/get")
    public ResponseData<?> get(@RequestParam String name) {
        return this.weatherService.getName(name);
    }

//    @CheckRole("USER,ADMIN")
//    @GetMapping("/get-all-city-name")
//    public ResponseData<?> getAllCityName() {
//        return this.weatherService.getAllCityName();
//    }

    @CheckRole("USER,ADMIN")
    @GetMapping("/get-by-names")
    public ResponseData<?> get(@RequestParam List<String> names) {
        return this.weatherService.getByNames(names);
    }

    @CheckRole("USER,ADMIN")
    @GetMapping("/get-all-city-name")
    public ResponseData<?> getAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return this.weatherService.getAll(page, size);
    }

}
