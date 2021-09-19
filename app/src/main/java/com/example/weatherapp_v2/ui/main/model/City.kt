package com.example.weatherapp_v2.ui.main.model

data class City(val name: String,
                val lat: Double,
                val lon: Double,
                val weather: Weather = Weather(0,0)
)