package ru.fesenko.helloweather

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale



import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController


import com.google.android.gms.location.LocationServices
import ru.fesenko.helloweather.network.CurrentWeatherResponse
import ru.fesenko.helloweather.network.HourlyForecastResponse
import ru.fesenko.helloweather.network.WeatherInfo

import android.Manifest
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.delay
import ru.fesenko.helloweather.weatherUI.WeatherScaffold
import ru.fesenko.helloweather.weatherUI.convertUnixToOmskTime

class MainActivity : ComponentActivity() {
//    companion object {
//        lateinit var globalVariable:  Pair<Double, Double>
//            private set
//
//        fun setGlobalVariable(value: Pair<Double, Double>) {
//            globalVariable = value
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//состояние для отрисовки сплеша
            var navigateToSecondScreen by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                delay(3000) // Переключение через 2 секунды
                navigateToSecondScreen = true
            }

            if (navigateToSecondScreen) {
                WeatherScaffold()
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                   SplashScreen(LocalContext.current)
                }
            }
        }
    }
    }

//@Composable
//fun SettingsScreen(viewModel: SettingsViewModel) {
//    val settings by viewModel.settings.collectAsState()
//    var brightness = settings?.brightness ?: 0
//
//    Column {
//        Text(text = "Current Brightness: $brightness")
//
//
//    }
//}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp() {
    val navController= rememberNavController()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Погода") }
            )
        },
        bottomBar = { BottomNavigation(navController =navController)
           },
        content = {
            NavGraph(navHostController = navController)
        }
    )
}

@Composable
fun WeatherIcon(icon: Painter) {
    Image(
        painter = icon,
        contentDescription = null,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape),
        contentScale = ContentScale.FillBounds
    )
}



@Composable
fun HourlyWeather() {
    LazyColumn {
        items(24) { hour ->
            HourlyWeatherItem(hour = hour, temperature = 20 + hour % 10)
        }
    }
}

@Composable
fun HourlyWeatherItem(hour: Int, temperature: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$hour:00")
        Text(text = "$temperature°C")
    }
}

//@Preview(showBackground = true)
//@Composable
////fun DefaultPreview() {
////    WeatherApp(weatherInfo)
////}

fun displayHourlyForecast(response: HourlyForecastResponse) {
    val hourlyForecastList = response.list
    for (hourlyForecastItem in hourlyForecastList) {
        val timestamp = (hourlyForecastItem.dt) // Время прогноза в формате Unix timestamp
        val temperature = hourlyForecastItem.main.temp// Температура
        val description = hourlyForecastItem.weather.first().description // Описание погоды


        // Вывод данных о погоде на каждый час
        Log.d("Hourly Forecast", "Time: $${convertUnixToOmskTime(timestamp)},timestamp, Temperature: $temperature, Description: $description")
    }
}

private fun displayCurrentWeather(response: CurrentWeatherResponse) {
    val id= response.weather[0].id
    val temperature = response.main.temp // Текущая температура
    val feelsLikeTemperature = response.main.feelsLike // Температура "ощущается как"
    val weatherDescription = response.weather.first().id // Описание погоды
    val windSpeed = response.wind.speed // Скорость ветра
    val visibility = response.visibility // Видимость
    val humidity = response.humidity // Влажность



    // Вывод информации о текущей погоде

    Log.d("Current Weather", "id: $id")
    Log.d("Current Weather", "Temperature: $temperature°C, Feels like: $feelsLikeTemperature°C")
    Log.d("Current Weather", "Description: $weatherDescription")
    Log.d("Current Weather", "Wind Speed: $windSpeed m/s")
    Log.d("Current Weather", "Visibility: $visibility meters")
    Log.d("Current Weather", "Humidity: $humidity%")
    Log.d("Current Weather", "Рассвет: ${response.sys.sunrise}")
    Log.d("Current Weather", "Закат: ${response.sys.sunset}")
    Log.d("Current Weather", "Направление ветра: ${getWindDirection(response.wind.deg)}")
}



fun displayCurrentWeatherView(response: CurrentWeatherResponse): WeatherInfo {
    val id= response.weather[0].id
    val temperature = response.main.temp
    val feelsLikeTemperature = response.main.feelsLike.toString()
    val weatherDescription = response.weather.first().description
    val windSpeed = response.wind.speed.toString()
    val visibility = response.visibility.toString()
    val pressure =response.wind.pressure
    val humidity = response.humidity.toString()
    val sunset = response.sys.sunset
    val sunrise = response.sys.sunrise

    return  WeatherInfo(
        id=id,
        temperature = temperature,
        feelsLikeTemperature = feelsLikeTemperature,
        weatherDescription = weatherDescription,
        windSpeed = windSpeed,
        pressure = pressure,
        visibility = visibility,
        humidity = humidity,
        sunset = sunset,
        sunrise = sunrise
    )
}

private fun getWindDirection(degrees: Double): String {
    val directions = arrayOf("С", "ССВ", "СВ", "ВСВ", "В", "ВЮВ", "ЮВ", "ЮЮВ", "Ю", "ЮЮЗ", "ЮЗ", "ЗЮЗ", "З", "ЗСЗ", "СЗ", "ССЗ")
    val index = ((degrees + 11.25) / 22.5).toInt() % 16
    return directions[index] 


}



fun getLastLocation(context: Context) {
    val fLocationClient=LocationServices.getFusedLocationProviderClient(context)

    // Токен
    val ct  =CancellationTokenSource()
    if (ActivityCompat.checkSelfPermission(
           context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    fLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, ct.token)
        .addOnCompleteListener { Log.d("1","${it.result.latitude}")
            Log.d("2","${it.result.longitude}")
        }


}

@Composable
fun LocationScreen() {

}
private fun requestLocation(context: Context) {
    try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    Log.d(TAG, "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                } else {
                    Log.d(TAG, "Location is null")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to get location: ${e.message}")
            }
    } catch (e: SecurityException) {
        Log.e(TAG, "SecurityException: ${e.message}")
    }
}

private const val TAG = "MainActivity"
private const val LOCATION_PERMISSION_REQUEST_CODE = 1001