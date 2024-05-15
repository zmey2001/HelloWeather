package ru.fesenko.helloweather.network

import retrofit2.http.GET
import retrofit2.http.Query



interface OpenWeatherMapService {
    // Получение прогноза погоды по часам
    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String= "a28824596b88979e3eacd8cedb5171d9"
    ): HourlyForecastResponse

    // Получение текущей погоды
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String= "a28824596b88979e3eacd8cedb5171d9"
    ): CurrentWeatherResponse
}
