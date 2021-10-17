package com.example.weatherapp_v2.ui.main.model

import com.example.weatherapp_v2.ui.main.model.database.HistoryDao
import com.example.weatherapp_v2.ui.main.model.database.HistoryEntity

class LocalRepositoryImpl(
    private val dao: HistoryDao
) : LocalRepository {

    override fun getAllHistory(): List<HistoryEntity> {
        return dao.all()
    }

    override fun saveEntity(city: HistoryEntity) {
        dao.insert(city)
    }

}