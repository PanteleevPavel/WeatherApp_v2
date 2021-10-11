package com.example.weatherapp_v2.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_v2.ui.main.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException

const val MAIN_LINK = "https://api.weather.yandex.ru/v2/forecast?"

class DetailViewModel : ViewModel() {

    private val repository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
    private val detailLiveData = MutableLiveData<AppState>()

    val liveData: LiveData<AppState> = detailLiveData

    fun getWeatherFromRemoteSource(city: City) {
        detailLiveData.value = AppState.Loading

        repository.getWeatherDetailFromServer(
            city.lat,
            city.lon,
            object : Callback<WeatherDTO> {
                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    detailLiveData.postValue(AppState.Error(t))
                }

                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    response.body()?.let { body ->
                        detailLiveData.postValue(checkResponse(body, city))
                    }
                }


            })
    }

    private fun checkResponse(response: WeatherDTO, city: City): AppState {
        val factDTO = response.fact

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