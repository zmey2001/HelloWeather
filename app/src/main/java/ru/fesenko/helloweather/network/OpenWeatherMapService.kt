package ru.fesenko.helloweather.network

import retrofit2.http.GET
import retrofit2.http.Query



interface OpenWeatherMapService {
    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("lang") language: String = "ru"
    ): HourlyForecastResponse

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "ru",
        @Query("appid") apiKey: String
    ): CurrentWeatherResponse
}
