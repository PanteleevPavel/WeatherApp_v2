package com.example.weatherapp_v2.ui.main.model

import com.example.weatherapp_v2.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val weatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                ))
                .build()
        )
        .build()
        .create(WeatherAPI::class.java)

    fun getWeatherDetail(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {

        weatherAPI.getWeather(BuildConfig.WEATHER_YANDEX_API_KEY, lat, lon).enqueue(callback)
    }

}