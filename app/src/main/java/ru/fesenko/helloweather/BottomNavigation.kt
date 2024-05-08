package ru.fesenko.helloweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation (navController: NavController) {


    val lisyItems = listOf(BottomItem.Screen1,
        BottomItem.Screen2
        )

    NavigationBar (modifier =  Modifier.background(Color.White)) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        lisyItems.forEach {item ->
            NavigationBarItem(selected = currentRoute==item.route, onClick = {
                navController.navigate(item.route)
            },
                icon = {
                    Icon(painter= painterResource(id = item.iconId),contentDescription="", modifier = Modifier
                        .size(32.dp)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Red,
                    selectedTextColor = Color.Red,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )

                )
        }

    }

}