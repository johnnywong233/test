package ws.weather.impl;

import ws.weather.WeatherService;

import javax.jws.WebService;

/**
 * Author: Johnny
 * Date: 2017/7/4
 * Time: 0:26
 */
@WebService(endpointInterface = "ws.weather.WeatherService", serviceName = "WeatherService")
public class WeatherServiceImpl implements WeatherService {
    @Override
    public String getWeather(String city) {
        //fake weather data:
        String shanghai = "12C, almost sunny";
        String beijing = "7C, most part foggy";
        String guangzhou = "20C, a little rainy";
        switch (city) {
            case "shanghai":
                return shanghai;
            case "beijing":
                return beijing;
            case "guangzhou":
                return guangzhou;
            default:
                return "no data";
        }
    }
}
