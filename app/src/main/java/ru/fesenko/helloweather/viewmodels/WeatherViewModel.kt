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
import ru.fesenko.helloweather.displayHourlyForecast
import ru.fesenko.helloweather.network.RetrofitInstance
import ru.fesenko.helloweather.network.WeatherInfo
import ru.fesenko.helloweather.weatherUI.WeatherSecond
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import ru.fesenko.helloweather.dataHandler.UnitConverter
import ru.fesenko.helloweather.network.CurrentWeatherResponse

private const val  YOUR_REQUEST_CODE = 1001
@SuppressLint("CoroutineCreationDuringComposition")
object  WeatherViewModel : ViewModel() {
    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo: LiveData<WeatherInfo> = _weatherInfo
    @Composable
    fun fetchWeather() {
        WeatherFirst.fetchCurrentLocation(LocalContext.current)
        val currentLocationPair by WeatherFirst.currentLocationLiveData.observeAsState()
        val service = RetrofitInstance.create()
        viewModelScope.launch {
            try {
                val response = service.getHourlyForecast("Omsk", "a28824596b88979e3eacd8cedb5171d9")
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
        val  unitConverter= UnitConverter(weatherInfo = weatherInfo ?: WeatherInfo())
        unitConverter.convertUnits()
        WeatherSecond(unitConverter)
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
    val feelsLikeTemperature = response.main.feelsLike.toString()
    val weatherDescription = response.weather.first().description
    val windSpeed = response.wind.speed
    val visibility = response.visibility
    val pressure =response.main.pressure
    val humidity = response.main.humidity.toString()
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



