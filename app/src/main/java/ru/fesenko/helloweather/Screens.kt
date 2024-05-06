package ru.fesenko.helloweather
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.fesenko.helloweather.WeatherUI.WeatherCard
import ru.fesenko.helloweather.WeatherUI.WeatherStart
import ru.fesenko.helloweather.network.RetrofitInstance
import ru.fesenko.helloweather.network.WeatherInfo
import kotlin.random.Random


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun  Screen2() {
    WeatherStart()
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun  Screen1(context: Context) {
    Text ( "122" , modifier = Modifier
        .padding(top= 100.dp))
    Log.d("Screen_2", "d")

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
                    MainActivity.setGlobalVariable(Pair(location.latitude, location.longitude))
                } else {
                    Log.d("Location", "Failed to get location")
                }
            }
    }
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

private const val  YOUR_REQUEST_CODE = 1001

