



package ru.fesenko.helloweather.weatherUI
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherFirst (context: Context) {
    Text ( "122" , modifier = Modifier
        .padding(top= 100.dp))
    Log.d("Screen_2", "d")
//
//    GlobalScope.launch(Dispatchers.IO) {
//
//        val fLocationClient = LocationServices.getFusedLocationProviderClient(context)
//        val ct = CancellationTokenSource()
//        // Проверка разрешений на местоположение
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            // TODO: Передать правильный activity и requestCode вместо 'context' и 'YOUR_REQUEST_CODE'
//            if (context is Activity) { // Проверяем, что context является Activity
//                ActivityCompat.requestPermissions(
//                    context,
//                    arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ),
//                    YOUR_REQUEST_CODE // Необходимо определить константу для кода запроса
//                )
//            }
//            return@launch
//        }
//
//        // Запрос текущего местоположения с высокой точностью
//        fLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, ct.token)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful && task.result != null) {
//                    val location = task.result
//                    Log.d(
//                        "Location",
//                        "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
//                    )
//                    MainActivity.setGlobalVariable(Pair(location.latitude, location.longitude))
//                } else {
//                    Log.d("Location", "Failed to get location")
//                }
//            }
//    }

}


//class WeatherViewModel : ViewModel() {
//
//    private val _currentLocation = MutableLiveData<Location?>()
//    val currentLocation: LiveData<Location?> = _currentLocation
//
//    fun fetchCurrentLocation(context: Context) {
//        val fLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//        if (fLocationClient == null) {
//            Log.d("Location", "Failed to get FusedLocationProviderClient")
//            return
//        }
//
//        // Проверка разрешений на местоположение
//        if (context.checkSelfPermission(
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED &&
//            context.checkSelfPermission(
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.d("Location", "Location permissions not granted")
//            return
//        }
//
//        // Запрос текущего местоположения с высокой точностью
//        GlobalScope.launch {
//            try {
//                val location = fLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
//                _currentLocation.postValue(location)
//            } catch (e: CancellationException) {
//                Log.d("Location", "Location request cancelled")
//            } catch (e: Exception) {
//                Log.e("Location", "Failed to get location: ${e.message}")
//            }
//        }
//    }
//}

private const val  YOUR_REQUEST_CODE = 1001