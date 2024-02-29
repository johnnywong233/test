package ws.weather;

import jakarta.jws.WebService;

/**
 * Author: Johnny
 * Date: 2017/7/4
 * Time: 0:26
 */
@WebService
public interface WeatherService {
    String getWeather(String city);
}
