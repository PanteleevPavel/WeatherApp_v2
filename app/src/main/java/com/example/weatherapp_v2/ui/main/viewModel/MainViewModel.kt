package com.example.weatherapp_v2.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_v2.ui.main.model.Repository
import com.example.weatherapp_v2.ui.main.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: Repository = RepositoryImpl()

    fun getData() = liveDataToObserve

    fun getCityFromLocalSource() = getDatFromLocalSource()

    private fun getDatFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(5000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getCityFromLocalStorage()))
        }.start()
    }
}