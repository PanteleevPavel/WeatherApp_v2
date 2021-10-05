package com.example.weatherapp_v2.ui.main.model

import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getWeatherDetailFromServer(requestList: String, callback: Callback) {
        remoteDataSource.getWeatherDetail(requestList, callback)
    }

}