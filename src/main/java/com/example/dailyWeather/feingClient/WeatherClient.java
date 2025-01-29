package com.example.dailyWeather.feingClient;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "weatherClient",
        url = "${weather.base-url}",
        fallback = WeatherFallback.class // Fallback mexanizmini qo‘shamiz
)
public interface WeatherClient {

    @GetMapping("/current.json")
    JsonNode getWeather(@RequestParam("key") String apiKey, @RequestParam("q") String city);
}
