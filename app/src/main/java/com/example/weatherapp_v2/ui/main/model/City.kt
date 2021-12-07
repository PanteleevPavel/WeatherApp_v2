package com.example.weatherapp_v2.ui.main.model

import android.os.Parcel
import android.os.Parcelable

data class City(
    val name: String? = "Default name",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val weather: Weather? = Weather(0, 0)
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readParcelable(Weather::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
        parcel.writeParcelable(weather, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}

fun getLocalCity(): List<City> = listOf(
    City(
        "Москва",
        55.7522,
        37.6156,
    ),
    City(
        "Санкт-Петербург",
        59.9386,
        30.3141,
    ),
    City(
        "Екатеринбург",
        56.8519,
        60.6122,
    ),
    City(
        "Сочи",
        43.5992,
        39.7257,
    ),
)