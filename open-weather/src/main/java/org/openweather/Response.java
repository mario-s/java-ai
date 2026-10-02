package org.openweather;

import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Optional.ofNullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Response(@JsonProperty("coord") Point p,
                       List<Weather> weather,
                       String base,
                       Main main,
                       int visibility,
                       Wind wind,
                       Clouds clouds,
                       Sys sys,
                       String name
                       ) {

    public static Response empty() {
        return new Response(
                new Point(0, 0),
                Collections.emptyList(),
                "",
                new Main(0, null, 0, 0, 0, 0, null, null),
                0,
                new Wind(0, 0, null),
                new Clouds(0),
                new Sys("", 0, 0),
                "N/A"
        );
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Point(double lat,
                        double lon) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(int id,
                          String main,
                          String description,
                          String icon) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(float temp,
                       @JsonProperty("feels_like") Float tempFeels,
                       @JsonProperty("temp_min") float tempMin,
                       @JsonProperty("temp_max") float tempMax,
                       float pressure,
                       float humidity,
                       @JsonProperty("sea_level") Float levelSea,
                       @JsonProperty("grnd_level") Float levelGround) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Wind(float speed,
                       float deg,
                       Float gust) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Clouds(float all) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Sys(String country,
                      long sunrise,
                      long sunset)  {}

    public String getCountry() {
        return ofNullable(sys).map(Sys::country).orElse("");
    }

    public float getTemperature() {
        return main.temp();
    }

    public float getWindSpeed() {
        return wind.speed();
    }

    public float getWindDirection() {
        return wind.deg();
    }

    public String getForecast() {
        try {
            return weather.getFirst().description();
        } catch (RuntimeException _) {
            return "";
        }
    }
}