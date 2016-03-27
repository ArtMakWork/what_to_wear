package com.artmakdeveloper.whattoweartoday.WeatherUtils;

import com.artmakdeveloper.whattoweartoday.MyLocationUtils.MyLocation;

public class WeatherHttpClient {
/* */
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" ;
    private static final String APIKEY = "&units=metric&APPID=e2cffe71733e54f1f083883c36f4987b" ;

    public String getLink(double Lat, double Lon) {
        return BASE_URL+Lat+"&lon="+Lon+APIKEY;
    }

    private static String answerServer = "null" ;

    public static String getAnswerServer() {
        return answerServer;
    }

    public static void setAnswerServer(String answerServer) {
        WeatherHttpClient.answerServer = answerServer;
    }



}