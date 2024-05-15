package ru.fesenko.helloweather

import android.annotation.SuppressLint

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier




import androidx.navigation.compose.rememberNavController
import android.Manifest
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import ru.fesenko.helloweather.weatherUI.WeatherScaffold

//Ну ту всё понятно
class MainActivity : ComponentActivity() {
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
                   SplashScreen(this@MainActivity)
                }
            }
        }
    }
}




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









