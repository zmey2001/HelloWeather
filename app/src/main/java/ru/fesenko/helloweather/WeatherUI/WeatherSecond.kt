package ru.fesenko.helloweather.WeatherUI

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.fesenko.helloweather.Histogram
import ru.fesenko.helloweather.MainActivity
import ru.fesenko.helloweather.displayCurrentWeatherView
import ru.fesenko.helloweather.displayHourlyForecast
import ru.fesenko.helloweather.network.RetrofitInstance
import ru.fesenko.helloweather.network.WeatherInfo
import kotlin.random.Random

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherSecond() {
    var weatherInfo by remember {
        mutableStateOf(
            WeatherInfo(
                800,
                0.0,
                "",
                "",
                "",
                "",0.toDouble(),
                "", 0, 0
            )
        )
    }

    val service = RetrofitInstance.create()
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = service.getHourlyForecast(
                "Omsk",
                "a28824596b88979e3eacd8cedb5171d9"
            )
//            displayHourlyForecast(response)
            val response2 = service.getCurrentWeather( MainActivity.globalVariable.first, MainActivity.globalVariable.second, apiKey = "a28824596b88979e3eacd8cedb5171d9")
//            displayCurrentWeather(response2)
            weatherInfo = displayCurrentWeatherView(response2)
            displayHourlyForecast(response)
            // Обработка полученного прогноза погоды по часам
        } catch (e: Exception) {
            Log.e("Weather", "Error: ${e.message}")
        }
    }

    Column(modifier = Modifier.padding(top = 60.dp, start = 10.dp, end = 10.dp)) {
        Text(
            text = "Прямо сейчас",
            fontSize = 45.sp
        )
        Text(
            text = "${weatherInfo.weatherDescription}",
            fontSize = 35.sp
        )
        Text(
            text = "Ветер ${weatherInfo.windSpeed}",
            fontSize = 35.sp
        )

        WeatherCard(weatherInfo)
        val randomValues = List(20) { Random.nextInt(30,50) }
        val maxValue = randomValues.maxOrNull() ?: 1
        Histogram(weatherInfo,randomValues, maxValue)


    }
}

