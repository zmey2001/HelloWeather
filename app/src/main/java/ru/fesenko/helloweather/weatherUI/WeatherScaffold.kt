package ru.fesenko.helloweather.weatherUI

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.fesenko.helloweather.BottomNavigation
import ru.fesenko.helloweather.NavGraph


//
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScaffold() {
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