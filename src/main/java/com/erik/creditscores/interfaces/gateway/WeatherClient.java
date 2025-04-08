package com.erik.creditscores.interfaces.gateway;

import com.erik.creditscores.interfaces.gateway.response.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "${weather.api.url}")
public interface WeatherClient {

    @GetMapping("/weather")
    WeatherResponse getWeather(@RequestParam("q") String cityName, @RequestParam("appid") String apiKey);

}