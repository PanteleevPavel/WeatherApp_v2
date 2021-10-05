package com.example.weatherapp_v2.ui.main.model

import com.example.weatherapp_v2.BuildConfig
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteDataSource {
    fun getWeatherDetail(link: String, callback: Callback) {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(link)
            .addHeader("X-Yandex-API-Key", BuildConfig.WEATHER_YANDEX_API_KEY)
            .get()
            .build()

        client.newCall(request).enqueue(callback)
    }

}