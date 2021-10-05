package com.example.weatherapp_v2.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_v2.ui.main.model.*
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.text.ParseException

const val MAIN_LINK = "https://api.weather.yandex.ru/v2/forecast?"

class DetailViewModel : ViewModel() {

    private val repository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
    private val detailLiveData = MutableLiveData<AppState>()

    val liveData: LiveData<AppState> = detailLiveData

    fun getWeatherFromRemoteSource(city: City) {
        detailLiveData.value = AppState.Loading

        repository.getWeatherDetailFromServer(
            MAIN_LINK + "lat=${city.lat}&lon=${city.lon}&lang=ru_RU", object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    detailLiveData.postValue(AppState.Error(e))
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body()?.string()?.let { body ->
                        detailLiveData.postValue(checkResponse(body, city))
                    }
                }

            })
    }

    private fun checkResponse(response: String, city: City): AppState {
        val weatherDTO = Gson().fromJson(response, WeatherDTO::class.java)
        val factDTO = weatherDTO.fact

        return if (factDTO != null) {
            AppState.Success(
                listOf(
                    City(
                        city.name,
                        city.lat,
                        city.lon,
                        Weather(factDTO.temp ?: 0, factDTO.feels_like ?: 0, factDTO.condition)
                    )
                )
            )
        } else {
            AppState.Error(ParseException("Не получилось распарсить json", 0))
        }
    }

}