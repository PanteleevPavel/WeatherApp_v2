package com.example.weatherapp_v2.ui.main.model

interface Repository {
    fun getCityFromLocalStorage(): List<City>
}