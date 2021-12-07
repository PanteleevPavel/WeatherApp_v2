package com.example.weatherapp_v2.ui.main.view

import android.app.Application
import androidx.room.Room
import com.example.weatherapp_v2.ui.main.model.database.HistoryDao
import com.example.weatherapp_v2.ui.main.model.database.HistoryDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                if (appInstance == null) throw IllegalStateException("APP_DROP")

                db = Room.databaseBuilder(
                    appInstance!!,
                    HistoryDataBase::class.java,
                    DB_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return db!!.historyDao()
        }
    }
}