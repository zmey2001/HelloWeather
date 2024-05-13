



package ru.fesenko.helloweather.weatherUI
import ru.fesenko.helloweather.viewmodels.WeatherFirst
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
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.fesenko.helloweather.viewmodels.WeatherViewModel
import kotlin.math.roundToLong


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherFirst (context: Context) {
    Row () {

        WeatherFirst.fetchCurrentLocation(LocalContext.current)
        val currentLocationPair by WeatherFirst.currentLocationLiveData.observeAsState()
        Text(
            text = "Ширина ${1}", modifier = Modifier
                .padding(top = 100.dp)
        )
        Text(
            text = "Долгота ${1}", modifier = Modifier
                .padding(top = 100.dp)
        )
    }
    Log.d("Screen_2", "d")
//
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