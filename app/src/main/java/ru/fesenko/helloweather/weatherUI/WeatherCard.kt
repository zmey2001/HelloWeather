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
                        model =  "https://openweathermap.org/img/wn/${getIconName(500)}.png",
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
//                    WheatherInfo(R.drawable.sun,"Восход ${convertUnixToOmskTime(weatherData.weatherInfo.sunrise)} --> Закат ${convertUnixToOmskTime(weatherInfo.sunset)} \n${convertUnixToOmskTime(weatherInfo.sunrise,weatherInfo.sunset)} часов дневного времени")
//                    WheatherInfo(R.drawable.droplet, "Влажность ${weatherInfo.humidity}%")
                    WheatherInfo(R.drawable.pressure, "Давление ${weatherData.pressureUnit}")
//                    WheatherInfo(R.drawable.cloud, "Видимость ${weatherInfo.visibility}")
                }
            }
        }
    }

}


val imageMap: Map<Int, Int> = mapOf(
    500 to R.drawable.rain,
    800 to R.drawable.sun,
    *(801..804).map { it to R.drawable.cloud }.toTypedArray(),

    )

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



fun convertUnixToOmskTime(unixTime: Long): String {
    // Создаем объект SimpleDateFormat
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    // Устанавливаем часовой пояс для Омска (GMT+6)
    sdf.timeZone = TimeZone.getTimeZone("Asia/Omsk")
    // Конвертируем UNIX время в строку с учетом часового пояса
    return sdf.format(Date( unixTime * 1000))
}

fun convertUnixToOmskTime(startUnixTime: Long, endUnixTime: Long): String {
    // Вычисляем разницу времени в секундах
    val timeDifferenceInSeconds = endUnixTime - startUnixTime
    // Конвертируем разницу времени в часы
    val hours = timeDifferenceInSeconds / 3600
    val minutes = (timeDifferenceInSeconds % 3600) / 60
    val seconds = timeDifferenceInSeconds % 60
    return "$hours часов $minutes минут"
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





