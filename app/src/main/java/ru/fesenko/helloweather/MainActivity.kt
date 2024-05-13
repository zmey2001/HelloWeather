package ru.fesenko.helloweather

import android.annotation.SuppressLint

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
import kotlinx.coroutines.delay
import ru.fesenko.helloweather.weatherUI.WeatherScaffold


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
    val navController = rememberNavController()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Погода") }
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },
        content = {
            NavGraph(navHostController = navController)
        }
    )
}









