package com.example.dailyWeather.repository;

import com.example.dailyWeather.entity.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    Optional<Weather> findByName(String name);

    @Query("SELECT w.name FROM Weather w")
    List<String> findAllNames();
}
