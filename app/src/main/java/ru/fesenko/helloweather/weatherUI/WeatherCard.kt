package ru.fesenko.helloweather.weatherUI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fesenko.helloweather.R
import ru.fesenko.helloweather.dataHandler.UnitConverter
import ru.fesenko.helloweather.network.WeatherInfo
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

import androidx.compose.ui.graphics.asImageBitmap
import coil.compose.AsyncImage


@Composable
fun WeatherCard(weatherData: UnitConverter) {
    var isExpanded by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            if (isExpanded) {
                Row {

                    AsyncImage(
                        model =  "https://openweathermap.org/img/wn/${getIconName(weatherData.weatherInfo.id)}.png",
                        contentDescription = null,

                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(width = 128.dp, height = 128.dp)
                            .align(Alignment.CenterVertically)
                            .clip(
                                RoundedCornerShape(
                                    200.dp// Маленький радиус для нижнего левого угла
                                )
                            )
                    )
                    Column {
                        Text(
                            text = weatherData.temperatureUnit,

                            fontSize = 55.sp
                        )
                        Text(
                            text = weatherData.feelsLikeTemperatureUnit,

                            fontSize = 20.sp
                        )


                    }
                }
            } else {
                Column {
                    WheatherInfo(R.drawable.sun,weatherData.sunriseANDsunsetUnit)
                    WheatherInfo(R.drawable.droplet, weatherData.humidityUnit)
                    WheatherInfo(R.drawable.pressure, weatherData.pressureUnit)
                  WheatherInfo(R.drawable.cloud, weatherData.visibilityUnit)
                }
            }
        }
    }

}

@Composable
fun WheatherInfo (imageId:Int, info:String) {
    Box() {
        Row (verticalAlignment = Alignment.CenterVertically){

            Image(
                painter = painterResource(id = imageId),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(30.dp)
                    .clip(
                        RoundedCornerShape(
                            200.dp// Маленький радиус для нижнего левого угла
                        )
                    )
            )
            Text(text = info)
        }
    }
}
private fun getIconName(weatherId: Int): String {
    return when (weatherId) {
        in 200..232 -> "11d" // гроза
        in 300..321 -> "09d" // морось
        in 500..531 -> "10d" // дождь
        in 600..622 -> "13d" // снег
        701, 721, 741 -> "50d" // туман/мгла
        800 -> "01d" // ясно
        in 801..804 -> "02d" // облачно
        else -> "01d" // по умолчанию, ясно
    }
}





