package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.clients.OpenWeatherClient;
import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.dto.response.WeatherApiDataResponse;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class LocationServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

    @MockBean
    private OpenWeatherClient openWeatherClient;

    @Autowired
    private LocationService out;

    @AfterEach
    void cleanDatabase() {
        sessionFactory.inTransaction(s -> {
            String querySession = "DELETE FROM Session";
            String queryLocation = "DELETE FROM Location";
            String queryUser = "DELETE FROM User";
            s.createMutationQuery(querySession).executeUpdate();
            s.createMutationQuery(queryLocation).executeUpdate();
            s.createMutationQuery(queryUser).executeUpdate();
        });
    }

    @Test
    void getForecastWhenGetForecastThenExpectedLocationReturned() {
        //        GIVEN
        Long userId = userService.save(new CreateUserFormData("login", "password")).getId();

        LocationSaveDto locationSaveDto = new LocationSaveDto("name", userId, BigDecimal.ZERO, BigDecimal.ZERO);
        out.save(locationSaveDto);

        WeatherApiDataResponse response = new WeatherApiDataResponse();
        response.setLocationId(1L);
        response.setLocationName("name");
        response.setCoord(new WeatherApiDataResponse.Coord());
        response.setMain(new WeatherApiDataResponse.Main());
        response.setWind(new WeatherApiDataResponse.Wind());

        when(openWeatherClient.getWeather(any(BigDecimal.class), any(BigDecimal.class)))
               .thenReturn(response);
        //        WHEN
        List<WeatherApiDataResponse> actual = out.getForecast(userId);
        //        THEN
        assertThat(actual.get(0))
                  .extracting("locationId", "locationName")
                  .containsExactly(1L, "name");
        verify(openWeatherClient, times(1)).getWeather(any(BigDecimal.class), any(BigDecimal.class));
    }

}