package com.example.weatherapp_v2.ui.main.model

data class WeatherDTO(
    val fact: FactDTO?
) {
    data class FactDTO(
        val temp: Int?,
        val feels_like: Int?,
        val condition: String?
    )
}
