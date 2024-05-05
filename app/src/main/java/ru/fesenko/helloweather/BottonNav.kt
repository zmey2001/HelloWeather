package ru.fesenko.helloweather

sealed class BottomItem (val title:String, val iconId: Int, val route: String) {
    object  Screen1 : BottomItem ("Screen1", R.drawable.ic_location,"screen_1")
    object  Screen2 : BottomItem ("Screen1", R.drawable.ic_weather_bottom,"screen_2")
}
