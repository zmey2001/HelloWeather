package ru.fesenko.helloweather.viewmodels
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import androidx.core.app.ActivityCompat
import android.util.Log
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch
import ru.fesenko.helloweather.network.RetrofitInstance
import ru.fesenko.helloweather.network.WeatherInfo
import ru.fesenko.helloweather.weatherUI.WeatherSecond
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import ru.fesenko.helloweather.dataHandler.UnitConverter
import ru.fesenko.helloweather.network.CurrentWeatherResponse
import ru.fesenko.helloweather.network.HourlyForecastResponse
import ru.fesenko.helloweather.weatherUI.convertUnixToOmskTime

private const val  YOUR_REQUEST_CODE = 1001
@SuppressLint("CoroutineCreationDuringComposition")
object  WeatherViewModel : ViewModel() {
    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo: LiveData<WeatherInfo> = _weatherInfo
    private val _hourlyForecast = MutableLiveData<List<WeatherInfo>>()
    val hourlyForecast:  LiveData<List<WeatherInfo>> = _hourlyForecast
    @Composable
    fun fetchWeather() {
        WeatherFirst.fetchCurrentLocation(LocalContext.current)
        val currentLocationPair by WeatherFirst.currentLocationLiveData.observeAsState()
        val service = RetrofitInstance.create()
        viewModelScope.launch {
            try {
                val response = service.getHourlyForecast(currentLocationPair!!.first,
                    currentLocationPair!!.second)
                // Обработка полученного прогноза погоды по часам
                // displayHourlyForecast(response)

                val response2 = service.getCurrentWeather(
                    currentLocationPair!!.first,
                    currentLocationPair!!.second
                )
                displayCurrentWeather(response2)
                // Обработка текущей погоды
                // displayCurrentWeather(response2)

                _weatherInfo.value = displayCurrentWeatherView(response2)
                displayHourlyForecast(response)
                _hourlyForecast.value= extractHourlyForecast(response)

            } catch (e: Exception) {
                Log.e("Weather", "Error: ${e.message}")
            }
        }
    }
}

object WeatherUIController {
    @Composable
    fun observeWeatherInfo() {
        val weatherViewModel = WeatherViewModel
        val weatherInfo by weatherViewModel.weatherInfo.observeAsState()
        val hourlyForecast by weatherViewModel.hourlyForecast.observeAsState()
        val  unitConverter= UnitConverter(weatherInfo = weatherInfo ?: WeatherInfo())
        unitConverter.convertUnits()
        val maxTemperature=findMaxTemperature(hourlyForecast)
        WeatherSecond(unitConverter,hourlyForecast,maxTemperature)
    }
}





object WeatherFirst: ViewModel()  {
    var currentLocationLiveData = MutableLiveData<Pair<Double, Double>>()
    fun fetchCurrentLocation(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val fLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val ct = CancellationTokenSource()

            // Проверка разрешений на местоположение
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Передать правильный activity и requestCode вместо 'context' и 'YOUR_REQUEST_CODE'
                if (context is Activity) { // Проверяем, что context является Activity
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        YOUR_REQUEST_CODE // Необходимо определить константу для кода запроса
                    )
                }
                return@launch
            }

            // Запрос текущего местоположения с высокой точностью
            fLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, ct.token)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val location = task.result
                        Log.d(
                            "Location",
                            "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
                        )
                        // Записываем координаты в MutableLiveData
                        currentLocationLiveData.postValue(Pair(location.latitude, location.longitude))
                        currentLocationLiveData.postValue(Pair(location.latitude, location.longitude))
                    } else {
                        Log.d("Location", "Failed to get location")
                    }
                }
        }
    }
}

fun displayCurrentWeatherView(response: CurrentWeatherResponse): WeatherInfo {
    val id= response.weather.first().id
    val temperature = response.main.temp
    val feelsLikeTemperature = response.main.feelsLike
    val weatherDescription = response.weather.first().description
    val windSpeed = response.wind.speed
    val visibility = response.visibility
    val pressure =response.main.pressure
    val humidity = response.main.humidity
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

private fun displayCurrentWeather(response: CurrentWeatherResponse) {
    val id= response.weather[0].id
    val temperature = response.main.temp // Текущая температура
    val feelsLikeTemperature = response.main.feelsLike // Температура "ощущается как"
    val weatherDescription = response.weather.first().id // Описание погоды
    val windSpeed = response.wind.speed // Скорость ветра
    val visibility = response.visibility // Видимость
    val humidity = response.main.humidity // Влажность

    // Вывод информации о текущей погоде

    Log.d("Current Weather", "id: $id")
    Log.d("Current Weather", "Temperature: $temperature°C, Feels like: $feelsLikeTemperature°C")
    Log.d("Current Weather", "Description: $weatherDescription")
    Log.d("Current Weather", "Wind Speed: $windSpeed m/s")
    Log.d("Current Weather", "Visibility: $visibility meters")
    Log.d("Current Weather", "Humidity: $humidity%")
    Log.d("Current Weather", "Рассвет: ${response.sys.sunrise}")
    Log.d("Current Weather", "Закат: ${response.sys.sunset}")
    Log.d("Current Weather", "Направление ветра: ${response.wind.deg}")
}


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
fun extractHourlyForecast(response: HourlyForecastResponse): List<WeatherInfo> {
    return response.list.map { hourlyForecastItem ->
        WeatherInfo(
            id = hourlyForecastItem.main.id,
            temperature = hourlyForecastItem.main.temp,
            feelsLikeTemperature = hourlyForecastItem.main.feelsLike,
            weatherDescription = hourlyForecastItem.weather.first().description,
            pressure = hourlyForecastItem.main.pressure,
            humidity = hourlyForecastItem.main.humidity,
        )
    }
}
fun findMaxTemperature(weatherInfoList: List<WeatherInfo>?): Int{
    // Проверяем, что список не пуст
    // Находим максимальное значение температуры
    val maxTemperatureWeatherInfo = weatherInfoList?.maxByOrNull { it.temperature } ?: return 0
    return maxTemperatureWeatherInfo.temperature.toInt()
}
