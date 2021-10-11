package com.example.weatherapp_v2.ui.main.model

import retrofit2.Callback


interface DetailsRepository {
    fun getWeatherDetailFromServer(lat: Double, lon: Double, callback: Callback<WeatherDTO>)
}