



package ru.fesenko.helloweather.weatherUI
import ru.fesenko.helloweather.viewmodels.GetCoord
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.recreate
import kotlin.math.roundToInt


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherFirst (context: Context) {

        GetCoord.fetchCurrentLocation(LocalContext.current)
        val currentLocationPair by GetCoord.currentLocationLiveData.observeAsState()
        val titleTextStyle = TextStyle(
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        val descriptionTextStyle = TextStyle(
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        val errorTextStyle = TextStyle(
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (currentLocationPair != null) {
                Row() {


                    Text(
                        text = "Ширина ${currentLocationPair?.first?.roundToInt()},",
//                        modifier = Modifier.padding(top = 100.dp),
                        style = titleTextStyle
                    )
                    Text(
                        text = " Долгота ${currentLocationPair?.second?.roundToInt()}",
//                        modifier = Modifier.padding(top = 16.dp),
                        style = titleTextStyle
                    )
                }
            } else {
                Text(
                    text = "Перезайдите в приложение",
                    modifier = Modifier.padding(top = 100.dp),
                    style = errorTextStyle
                )
            }
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
