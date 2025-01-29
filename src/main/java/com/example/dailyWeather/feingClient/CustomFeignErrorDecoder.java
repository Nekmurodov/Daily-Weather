package com.example.dailyWeather.feingClient;

import com.example.dailyWeather.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.rmi.ServerException;

@Component
public class CustomFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        return switch (status) {
            case NOT_FOUND -> new NotFoundException("City not found: " + methodKey);
            case BAD_REQUEST -> new BadRequestException("Invalid request: " + methodKey);
            case INTERNAL_SERVER_ERROR -> new ServerException("Weather API error: " + methodKey);
            default -> new RuntimeException("Unexpected error: " + methodKey);
        };
    }
}
