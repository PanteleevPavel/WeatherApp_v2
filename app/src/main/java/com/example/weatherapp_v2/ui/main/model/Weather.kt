package com.example.weatherapp_v2.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(var temperature: Int = 0, var feelsLike: Int = 0) : Parcelable