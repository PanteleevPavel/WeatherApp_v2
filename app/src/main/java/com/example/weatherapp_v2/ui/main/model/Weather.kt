package com.example.weatherapp_v2.ui.main.model

import android.os.Parcel
import android.os.Parcelable

data class Weather(
    var temperature: Int = 0,
    var feelsLike: Int = 0,
    var condition: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(temperature)
        parcel.writeInt(feelsLike)
        parcel.writeString(condition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}