package com.example.dailyWeather.feingClient;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.stereotype.Component;

@Component
public class WeatherFallback implements WeatherClient {

    @Override
    public JsonNode getWeather(String city) {
        JsonNodeFactory factory = JsonNodeFactory.instance;

        return factory.objectNode()
                .putObject("location")
                .put("name", city)
                .put("country", "Unknown")
                .put("lat", 0.0)
                .put("lon", 0.0)
                .set("current", factory.objectNode()
                        .put("temp_c", 0.0)
                        .put("wind_kph", 0.0)
                        .put("cloud", 0));
    }
}