package com.example.weatherapp.data.internet;

import com.example.weatherapp.data.entity.current.CurrentWeather;
import com.example.weatherapp.data.entity.forecast.ForecastEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.weatherapp.data.internet.ApiEndPoints.CURRENT;
import static com.example.weatherapp.data.internet.ApiEndPoints.FORECAST;

public interface RetrofitService {
    @GET(CURRENT)
Call<CurrentWeather>fetchtCurrentWeather(@Query("q") String city,
                                         @Query("appid") String appid,
                                         @Query("units") String metric);

    @GET(FORECAST)
    Call<ForecastEntity> getForecast(@Query("q") String city,
                                     @Query("appid") String appid,
                                     @Query("units") String inits);

    @GET(CURRENT)
    Call<CurrentWeather> coordCurrentWeather(@Query("lat") double lat,
                                             @Query("lon") double lon,
                                             @Query("units") String format,
                                             @Query("appid") String key);
    @GET(FORECAST)
    Call<ForecastEntity> coordForCastWeather(@Query("lat") double lat,
                                            @Query("lon") double lon,
                                            @Query("units") String format,
                                            @Query("appid") String key);
}
