package com.example.weatherapp_v2.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name: String = "Default name",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val weather: Weather = Weather(0, 0)
) : Parcelable

fun getLocalCity(): List<City> = listOf(
    City(
        "Москва",
        55.7522,
        37.6156,
    ),
    City(
        "Санкт-Петербург",
        59.9386,
        30.3141,
    ),
    City(
        "Екатеринбург",
        56.8519,
        60.6122,
    ),
    City(
        "Сочи",
        43.5992,
        39.7257,
    ),
)