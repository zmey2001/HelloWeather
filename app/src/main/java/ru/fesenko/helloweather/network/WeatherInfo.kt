package ru.fesenko.helloweather.network

data class WeatherInfo(
    val id: Int,
    val temperature: Double,
    val feelsLikeTemperature: String,
    val weatherDescription: String,
    val windSpeed: String,
    val visibility: String,
   val pressure: Double,
    val humidity: String,
    val sunrise: Long, // Время восхода солнца в формате Unix timestamp (секунды)
    val sunset: Long // Время захода солнца в формате Unix timestamp (секунды)
)

