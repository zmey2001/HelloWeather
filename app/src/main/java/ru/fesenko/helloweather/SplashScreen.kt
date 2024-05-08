package ru.fesenko.helloweather
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.fesenko.helloweather.R
import ru.fesenko.helloweather.network.WeatherInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun  SplashScreen (context: Context){
//Парсим массив пожеланий
    val weatherWishesArray = context.resources.getStringArray(R.array.weather_wishes_array)

// Теперь у вас есть случайное пожелание и соответствующее изображение

    val randomIndex = (0 until weatherWishesArray.size).random()
    val randomWeatherWish = weatherWishesArray[randomIndex]

    // Выводим случайное пожелание и соответствующее изображение
    Column  (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
     Text(text = randomWeatherWish, fontSize = 30.sp, fontStyle = FontStyle.Italic, modifier = Modifier.fillMaxWidth().padding(start = 10.dp) )
        Image(
            painter =   painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(500.dp)
        )
    }
}

