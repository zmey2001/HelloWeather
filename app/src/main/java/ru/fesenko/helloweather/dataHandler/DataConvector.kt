package ru.fesenko.helloweather.dataHandler

import ru.fesenko.helloweather.network.WeatherInfo
import kotlin.math.roundToInt

class UnitConverter(
    var temperatureUnit: String="",
    var speedUnit: String ="",
    var visibilityUnit: String="",
    var pressureUnit: String="",
    var feelsLikeTemperatureUnit: String="",
    var weatherInfo: WeatherInfo

) {
    fun convertUnits(){
        temperatureUnit= convertTemperature(2,weatherInfo.temperature)
        speedUnit= convertSpeed(1,weatherInfo.windSpeed)
        visibilityUnit= convertVisibility(1,weatherInfo.visibility)
        pressureUnit=convertPressure(1,weatherInfo.pressure)
        feelsLikeTemperatureUnit= "Чувствется как ${temperatureUnit}"
    }

    private fun convertTemperature(unit: Int, value: Double): String {
        return when (unit) {
            1 -> "${value.roundToInt() }°C" // Цельсий
            2 -> "${(value - 273.15).roundToInt()} °C" // Фаренгейт в Цельсий
            3 -> "${value + 273.15} K"  // Цельсий в Кельвин
            else -> value.toString()
        }
    }

    private fun convertSpeed( unit: Int,value: Double): String {
        return when (unit) {
            1 -> "$value м/с"
            2 -> "${value * 0.44704} м/с"
            else -> value.toString()
        }
    }
    private fun convertVisibility(unit: Int, value: Int): String {
        return when (unit) {
            1 -> "$value км"
            2 -> "${value * 1.60934} км"
            else -> value.toString()
        }
    }

    private fun convertPressure( unit: Int, value: Double): String {
        return when (unit) {
            1 -> "$value гПа"
            2 -> "${value * 33.8639} мм рт. ст."
            else -> value.toString()
        }
    }
}