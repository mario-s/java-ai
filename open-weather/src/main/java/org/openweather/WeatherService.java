package org.openweather;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class WeatherService {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private final ApiClient apiClient;

    @Autowired
    public WeatherService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get the current weather for a specific latitude/longitude
     * @param latitude Latitude
     * @param longitude Longitude
     * @return The weather for the given location
     * @throws RestClientException if the request fails
     */
    @Tool(description = "Get current weather for a specific latitude/longitude")
    public String getCurrentWeather(@ToolParam(description = "latitude in deximaL degree") double latitude,
                                    @ToolParam(description = "longitude in deximaL degree") double longitude) {
        String weatherText = "";

        try {
            Response res = apiClient.getCurrentWeather(latitude, longitude);
            LOG.debug("weather response: {}", res);
            weatherText =
                    String.format("%s, %s: Temperature: %s °C Wind: %s %s Forecast: %s",
                            res.getCountry(),
                            res.name(),
                            res.getTemperature(),
                            res.getWindSpeed(),
                            res.getWindDirection(),
                            res.getForecast());
        } catch (RestClientException e) {
            LOG.warn(e.getMessage(), e);
            weatherText = e.getMessage();
        }

        return weatherText;
    }
}