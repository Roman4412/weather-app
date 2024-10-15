package com.pustovalov.weatherapplication.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pustovalov.weatherapplication.dto.response.WeatherData;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WeatherDataDeserializer extends JsonDeserializer<WeatherData> {

    @Override
    public WeatherData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws
            IOException {

        TreeNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").toString();
        String temperature = node.get("main").get("temp").toString();
        String windSpeed = node.get("wind").get("speed").toString();
        String humidity = node.get("main").get("humidity").toString();
        String pressure = node.get("main").get("pressure").toString();

        return new WeatherData(name, temperature, windSpeed, humidity, pressure);
    }
}
