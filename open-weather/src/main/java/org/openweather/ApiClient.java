package org.openweather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
class ApiClient {

    private final AppConfig appConfig;
    private final RestClient restClient;

    @Autowired
    ApiClient(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.restClient = RestClient.builder()
                .baseUrl(appConfig.getBaseUrl())
                .defaultHeader("Accept", "application/geo+json")
                .defaultHeader("User-Agent", "OpenWeatherApiClient/1.0.0")
                .build();
    }

    Response getCurrentWeather(double latitude, double longitude) {
        var units = "metric";
        var exclude = "minutely;hourly;daily;alerts";

        return restClient.get()
                .uri("?lat={lat}&lon={lon}&exclude={part}&units={units}&appid={API key}",
                        latitude, longitude, exclude, units, appConfig.getApiKey())
                .retrieve()
                .body(Response.class);
    }
}
