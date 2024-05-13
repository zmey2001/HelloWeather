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
}



