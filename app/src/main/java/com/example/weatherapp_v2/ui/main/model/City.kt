package com.example.weatherapp_v2.ui.main.model

import android.os.Parcel
import android.os.Parcelable
import kotlin.random.Random

data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    val weather: Weather = Weather(0, 0)
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readParcelable(Weather::class.java.classLoader)!!
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
        Weather(Random.nextInt(-30, 30), Random.nextInt(-30, 30))
    ),
    City(
        "Санкт-Петербург",
        59.9386,
        30.3141,
        Weather(Random.nextInt(-30, 30), Random.nextInt(-30, 30))
    ),
    City(
        "Екатеринбург",
        56.8519,
        60.6122,
        Weather(Random.nextInt(-30, 30), Random.nextInt(-30, 30))
    ),
    City(
        "Сочи",
        43.5992,
        39.7257,
        Weather(Random.nextInt(-30, 30), Random.nextInt(-30, 30))
    ),
)