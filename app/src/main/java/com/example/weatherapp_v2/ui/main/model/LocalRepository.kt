package com.example.weatherapp_v2.ui.main.model

import com.example.weatherapp_v2.ui.main.model.database.HistoryEntity

interface LocalRepository {

    fun getAllHistory(): List<HistoryEntity>
    fun saveEntity(city: HistoryEntity)

}