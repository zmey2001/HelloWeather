package ru.fesenko.helloweather.weatherUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.fesenko.helloweather.R
import ru.fesenko.helloweather.dataHandler.UnitConverter
import ru.fesenko.helloweather.network.WeatherInfo


@Composable
fun WeatherSecond(
    weatherData: UnitConverter,
//    hourlyForecast: List<WeatherInfo>?,
//    maxTemperature: Int
) {
    Column(modifier = Modifier.padding(top = 60.dp, start = 10.dp, end = 10.dp)) {
        Text(
            text = "${LocalContext.current.resources.getString(R.string.right_now)}",
            fontSize = 45.sp
        )
        Text(
            text = "${weatherData.weatherInfo.weatherDescription}",
            fontSize = 35.sp
        )
        Text(
            text = "${LocalContext.current.resources.getString(R.string.wind_info)} ${weatherData.speedUnit}",
            fontSize = 35.sp
        )
        // Другие текстовые элементы для отображения данных о погоде
        WeatherCard(weatherData)
//        if (hourlyForecast!!.isEmpty() != true) {
//            WeatherHistogram(hourlyForecast, maxTemperature)
//        }
//
    }
}
