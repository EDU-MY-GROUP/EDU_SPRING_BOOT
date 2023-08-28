package com.example.demo.RestfulController.C02OpenWeather;


import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


//OpenWeatherAPI Document
//https://openweathermap.org/api/one-call-3#how



@RestController
@Slf4j
public class C03OpenWeatherController {
    private static final String API_KEY = "b7a263e63bfe790ff0081e9b619e7c91";
    private final RestTemplate restTemplate = new RestTemplate();


    @GetMapping(value="/OpenWeatherAPI/getWeather/{lat}/{lon}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public Object CurrentWeather(@PathVariable String lat,@PathVariable String lon) {

        //API Doc https://openweathermap.org/current

        System.out.println("");
        //위도 경도 확인 http://map.esran.com/
        //샘플: 35.87012377947339, 128.57868733602822

        String url = "https://api.openweathermap.org/data/2.5/weather?";
        url+="lat="+lat;
        url+="&lon="+lon;
        url+="&appid="+API_KEY;


//            JSON to JAVA Convert 싸이트를 이용해서 Converting 하기



        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

        System.out.println("getWeather : " + weatherResponse);


        return weatherResponse;
    }

}

//현재 날씨 데이터: 특정 위치의 실시간 날씨 정보를 가져올 수 있습니다. 이 데이터에는 온도, 습도, 기압, 풍속 등이 포함됩니다.
//일기 예보: 특정 위치의 일기 예보를 가져올 수 있으며, 주로 5일에서 7일까지의 예보 데이터를 제공합니다.
//날씨 맵: 기상 레이더, 구름 상태, 강수량 등의 날씨 맵 정보를 얻을 수 있습니다.
//기상 조건 코드: 날씨 상태를 식별하는 데 사용되는 코드 목록을 제공합니다. 예를 들면, "맑음", "흐림", "비", "눈" 등이 있습니다.
//지난 날씨 데이터: 특정 날짜의 과거 날씨 데이터를 확인할 수 있습니다.
//위치 검색: 특정 지역이나 도시의 위도와 경도, 이름 등을 검색하여 날씨 데이터를 가져올 수 있습니다.




////Convert JSON -> JAVA Site
////https://codebeautify.org/json-to-java-converter#

@Data
@ToString
class WeatherResponse {
    Coord Coord;
    ArrayList < Object > weather = new ArrayList < Object > ();
    private String base;
    Main Main;
    private float visibility;
    Wind Wind;
    Clouds Clouds;
    private float dt;
    Sys Sys;
    private float timezone;
    private float id;
    private String name;
    private float cod;

    @Data
    public class Sys {
        private float type;
        private float id;
        private String country;
        private float sunrise;
        private float sunset;
    }
    @Data
    public class Clouds {
        private float all;
    }
    @Data
    public class Wind {
        private float speed;
        private float deg;
    }
    @Data
    public class Main {
        private float temp;
        private float feels_like;
        private float temp_min;
        private float temp_max;
        private float pressure;
        private float humidity;
    }

    @Data
    public class Coord {
        private float lon;
        private float lat;

    }
}



