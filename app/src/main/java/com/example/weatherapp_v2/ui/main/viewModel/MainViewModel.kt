package com.example.weatherapp_v2.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_v2.ui.main.model.*
import java.lang.Thread.sleep

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: Repository = RepositoryImpl()

    fun getData() = liveDataToObserve

    fun getCityFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        val cityList: List<City> = getCityWithTemperature(repositoryImpl.getCityFromLocalStorage())
        Thread {
            sleep(5000)
            liveDataToObserve.postValue(AppState.Success(cityList))
        }.start()
    }

    private fun getCityWithTemperature(cityList: List<City>): List<City> {
        for (city in cityList) {
            WeatherLoader(
                city.lat,
                city.lon,
                object : WeatherLoader.WeatherLoaderListener {
                    override fun onLoaded(weatherDTO: WeatherDTO) {
                        city.weather.apply {
                            temperature = weatherDTO.fact?.temp ?: 0
                            feelsLike = weatherDTO.fact?.feels_like ?: 0
                        }
                    }

                    override fun onFailed(throwable: Throwable) {
                        city.weather.apply {
                            temperature = 0
                            feelsLike = 0
                        }
                    }
                }
            ).goToInternet()
        }
        return cityList
    }
}