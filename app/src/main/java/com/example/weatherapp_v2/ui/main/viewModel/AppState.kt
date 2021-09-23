package com.example.weatherapp_v2.ui.main.viewModel

import com.example.weatherapp_v2.ui.main.model.City

sealed class AppState {
    data class Success(val cityData: List<City>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
