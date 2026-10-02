package org.openweather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherServiceTest {
    private static final double LATITUDE = 40.7128;
    private static final double LONGITUDE = -74.006;

    @Autowired
    private WeatherService service;

    @MockitoBean
    private ApiClient apiClient;

    @Test
    @DisplayName("It should return the current weather for a given location.")
    void getCurrentWeather() {
        when(apiClient.getCurrentWeather(LATITUDE, LONGITUDE)).thenReturn(Response.empty());

        String result = service.getCurrentWeather(LATITUDE, LONGITUDE);

        assertFalse(result.isEmpty(), "Expected a current weather report");
    }
}
