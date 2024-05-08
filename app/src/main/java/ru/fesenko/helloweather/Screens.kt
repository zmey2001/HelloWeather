package ru.fesenko.helloweather
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.fesenko.helloweather.weatherUI.WeatherSecond
import ru.fesenko.helloweather.weatherUI.WeatherFirst
import ru.fesenko.helloweather.network.WeatherInfo
import ru.fesenko.helloweather.viewmodels.WeatherUIController
import ru.fesenko.helloweather.viewmodels.WeatherViewModel



@Composable
fun  Screen2() {
    WeatherUIController.observeWeatherInfo()
    WeatherViewModel.fetchWeather()


}
@Composable
fun  Screen1() {
    WeatherFirst(LocalContext.current)
    WeatherViewModel.fetchWeather()

}

@Composable
fun Histogram(weatherInfo: WeatherInfo, randomValues: List<Int>, maxValue: Int) {
    LazyRow  ( modifier = Modifier.padding(bottom=100.dp)) {
        items(randomValues.size) { index ->
            Column(
                modifier = Modifier
                    .width(70.dp)
                    .padding(start = 0.dp, end = 0.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .width(70.dp)
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Box(
                     // Круглая форма для карточки

                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp * (randomValues[index].toFloat() / maxValue)).clip(
                                RoundedCornerShape(
                                    topStart = 10.dp, // Большой радиус для верхнего левого угла
                                    topEnd = 10.dp, // Маленький радиус для верхнего правого угла
                                   // Маленький радиус для нижнего левого угла
                                )
                            )
                            .background(Color.Green),
                        contentAlignment = Alignment.Center,




                        ) {

                        Text(text = "${weatherInfo.temperature-273}")
                    }

                }
                Text(text = "11:00",     modifier = Modifier
                    .align(Alignment.CenterHorizontally))
                Image(
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier

                        .size(width = 64.dp, height = 46.dp).align(Alignment.CenterHorizontally)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp, // Большой радиус для верхнего левого угла
                                topEnd = 0.dp, // Маленький радиус для верхнего правого угла
                                bottomEnd = 30.dp, // Большой радиус для нижнего правого угла
                                bottomStart = 30.dp // Маленький радиус для нижнего левого угла
                            )
                        )
                )

        }
    }
        }

}



