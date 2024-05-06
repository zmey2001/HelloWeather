





package ru.fesenko.helloweather.WeatherUI

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.fesenko.helloweather.MainActivity


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherFirst (context: Context) {
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
private const val  YOUR_REQUEST_CODE = 1001