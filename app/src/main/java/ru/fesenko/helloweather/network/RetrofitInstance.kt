package ru.fesenko.helloweather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
//    private const val API_KEY = "YOUR_API_KEY"

    fun create(): OpenWeatherMapService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OpenWeatherMapService::class.java)
    }
}
