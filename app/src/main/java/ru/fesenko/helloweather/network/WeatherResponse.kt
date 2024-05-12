package ru.fesenko.helloweather.network

data class CurrentWeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
   // Влажность (в процентах)
    val sys: Sys,
    val visibility: Int,
)

data class HourlyForecastResponse(
    val list: List<HourlyForecastItem>
)

data class HourlyForecastItem(
    val dt: Long, // Время прогноза в формате Unix timestamp
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double, // Температура
    val feelsLike: Double,
    val id: Int,
    val pressure: Double,
     // Видимость (в метрах)
    val humidity: Int
    // Другие параметры, такие как давление, влажность и т.д.
)
data class Wind(
    val speed: Double,// Скорость ветра
    val deg: Double,
    val pressure: Double
)
data class Weather(
    val description: String, // Описание погоды
    val id: Int
    // Другие параметры погоды, такие как иконка, осадки и т.д.
)

data class Sys(
    val sunrise: Long, // Время восхода солнца в формате Unix timestamp (секунды)
    val sunset: Long // Время захода солнца в формате Unix timestamp (секунды)
)


