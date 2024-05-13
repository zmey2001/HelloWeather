package ru.fesenko.helloweather.dataHandler

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import ru.fesenko.helloweather.R
import ru.fesenko.helloweather.network.WeatherInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

class UnitConverter(
    var temperatureUnit: String="",
    var speedUnit: String ="",
    var visibilityUnit: String="",
    var pressureUnit: String="",
    var feelsLikeTemperatureUnit: String="",
    var sunriseANDsunsetUnit: String="",
    var humidityUnit:String="",
    var weatherDescriptionUnit:  String="",
    var weatherInfo: WeatherInfo,

) {
    @Composable
    fun convertUnits(){
        temperatureUnit= convertTemperature(1,weatherInfo.temperature)
        speedUnit= convertSpeed(1,weatherInfo.windSpeed)
        visibilityUnit= "${LocalContext.current.resources.getString(R.string.visibility)} ${convertVisibility(1,weatherInfo.visibility)}"
        pressureUnit=convertPressure(1,weatherInfo.pressure)
        feelsLikeTemperatureUnit= "${LocalContext.current.resources.getString(R.string.feels_like)} ${temperatureUnit}"
        sunriseANDsunsetUnit=calculationDay(weatherInfo.sunrise,weatherInfo.sunset)
        weatherDescriptionUnit=weatherInfo.weatherDescription.replaceFirstChar { it.uppercase() }
        humidityUnit="${LocalContext.current.resources.getString(R.string.humidity)} ${weatherInfo.humidity}%"

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
            1 -> "$value м"
            2 -> "${value * 1.60934} м"
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
    private fun convertUnixToOmskTime(unixTime: Long): String {
        // Создаем объект SimpleDateFormat
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        // Устанавливаем часовой пояс для Омска (GMT+6)
        sdf.timeZone = TimeZone.getTimeZone("Asia/Omsk")
        // Конвертируем UNIX время в строку с учетом часового пояса
        return sdf.format(Date( unixTime * 1000))
    }
    private fun convertUnixToOmskTime(startUnixTime: Long, endUnixTime: Long): String {
        // Вычисляем разницу времени в секундах
        val timeDifferenceInSeconds = endUnixTime - startUnixTime
        // Конвертируем разницу времени в часы
        val hours = timeDifferenceInSeconds / 3600
        val minutes = (timeDifferenceInSeconds % 3600) / 60
        val seconds = timeDifferenceInSeconds % 60
        return "$hours часов $minutes минут"
    }
    @Composable
    private fun calculationDay(startUnixTime: Long, endUnixTime: Long): String {
        // Вычисляем разницу времени в секундах
        return "${LocalContext.current.resources.getString(R.string.sunrise)} - ${convertUnixToOmskTime(weatherInfo.sunrise)} --> ${LocalContext.current.resources.getString(R.string.sunset)} ${
            convertUnixToOmskTime(
                weatherInfo.sunset
            )
        } \n${
            convertUnixToOmskTime(
                weatherInfo.sunrise,
                weatherInfo.sunset
            )
        } ${LocalContext.current.resources.getString(R.string.duration_day)}"
    }
    private fun getWindDirection(degrees: Double): String {
        val directions = arrayOf("С", "ССВ", "СВ", "ВСВ", "В", "ВЮВ", "ЮВ", "ЮЮВ", "Ю", "ЮЮЗ", "ЮЗ", "ЗЮЗ", "З", "ЗСЗ", "СЗ", "ССЗ")
        val index = ((degrees + 11.25) / 22.5).toInt() % 16
        return directions[index]


    }
}