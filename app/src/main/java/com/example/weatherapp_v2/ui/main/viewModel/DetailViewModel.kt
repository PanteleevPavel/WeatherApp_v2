package com.example.weatherapp_v2.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_v2.ui.main.model.*
import com.example.weatherapp_v2.ui.main.model.database.HistoryEntity
import com.example.weatherapp_v2.ui.main.view.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.util.*

class DetailViewModel : ViewModel() {

    private val repository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())
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
                        Weather(
                            factDTO.temp ?: 0,
                            factDTO.feels_like ?: 0,
                            factDTO.condition ?: "error load condition"
                        )
                    )
                )
            )
        } else {
            AppState.Error(ParseException("Не получилось распарсить json", 0))
        }
    }

    fun saveWeather(city: City) {
        city.name?.let {
            city.weather?.let { it1 ->
                city.weather.condition?.let { it2 ->
                    HistoryEntity(
                        0,
                        it,
                        it1.temperature,
                        it2,
                        Date().time,
                    )
                }
            }
        }?.let {
            localRepository.saveEntity(
                it
            )
        }
    }

}