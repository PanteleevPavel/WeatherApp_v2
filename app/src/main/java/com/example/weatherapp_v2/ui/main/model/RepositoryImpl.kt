package com.example.weatherapp_v2.ui.main.model

class RepositoryImpl : Repository {

    override fun getCityFromLocalStorage(): List<City> = getLocalCity()

}