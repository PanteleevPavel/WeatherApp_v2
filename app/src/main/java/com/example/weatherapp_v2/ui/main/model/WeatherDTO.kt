package com.example.weatherapp_v2.ui.main.model

import android.os.Parcel
import android.os.Parcelable

data class WeatherDTO(
    val fact: FactDTO?
) {
    data class FactDTO(
        val temp: Int?,
        val feels_like: Int?,
        val condition: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(temp)
            parcel.writeValue(feels_like)
            parcel.writeString(condition)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<FactDTO> {
            override fun createFromParcel(parcel: Parcel): FactDTO {
                return FactDTO(parcel)
            }

            override fun newArray(size: Int): Array<FactDTO?> {
                return arrayOfNulls(size)
            }
        }
    }
}
