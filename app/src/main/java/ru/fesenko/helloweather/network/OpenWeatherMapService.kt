package ru.fesenko.helloweather.network

import retrofit2.http.GET
import retrofit2.http.Query



interface OpenWeatherMapService {
    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String= "a28824596b88979e3eacd8cedb5171d9"
    ): HourlyForecastResponse

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String= "a28824596b88979e3eacd8cedb5171d9"
    ): CurrentWeatherResponse
}
