package ru.fesenko.helloweather

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "screen_1") {

    composable ("screen_1"){
        Screen1()


    }
        composable ("screen_2"){
            Screen2()


        }
    }




}