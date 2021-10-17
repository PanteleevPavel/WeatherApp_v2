package com.example.weatherapp_v2.ui.main.viewModel

import androidx.lifecycle.ViewModel
import com.example.weatherapp_v2.ui.main.model.LocalRepositoryImpl
import com.example.weatherapp_v2.ui.main.model.database.HistoryEntity
import com.example.weatherapp_v2.ui.main.view.App

class HistoryViewModel : ViewModel() {

    private val historyRepository = LocalRepositoryImpl(App.getHistoryDao())

    fun getAllHistory(): List<HistoryEntity> = historyRepository.getAllHistory()

}