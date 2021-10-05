package com.example.weatherapp_v2.ui.main.model

import okhttp3.Callback

interface DetailsRepository {
    fun getWeatherDetailFromServer(requestList: String, callback: Callback)
}