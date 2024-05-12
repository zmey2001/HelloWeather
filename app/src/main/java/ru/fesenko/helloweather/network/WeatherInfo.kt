package ru.fesenko.helloweather.network

data class WeatherInfo(
    val id: Int=0,
    val temperature: Double=0.0,
    val feelsLikeTemperature: String="",
    val weatherDescription: String="",
    val windSpeed: Double=0.0,
    val visibility: Int=0,
   val pressure: Double=0.0,
    val humidity: String="",
    val sunrise: Long= 0L, // Время восхода солнца в формате Unix timestamp (секунды)
    val sunset: Long= 0L // Время захода солнца в формате Unix timestamp (секунды)
)

