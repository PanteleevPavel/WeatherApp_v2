package com.example.weatherapp_v2.ui.main.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_v2.R
import com.example.weatherapp_v2.ui.main.model.City
import com.example.weatherapp_v2.ui.main.model.WeatherType
import com.example.weatherapp_v2.ui.main.model.WeatherType.*

class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    var cityList: List<City> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_fragment, parent, false)
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int = cityList.size

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(city: City) {
            itemView.apply {
                findViewById<TextView>(R.id.town).text = city.name
                findViewById<TextView>(R.id.temp).text = "${city.weather.temperature}°"
                findViewById<TextView>(R.id.space)
                    .setBackgroundColor(
                        getColor(
                            if (city.weather.temperature <= -5) SNOWY
                            else if (city.weather.temperature > -5 && city.weather.temperature < 10) RAINY
                            else SUNNY
                        )
                    )
                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(city)
                }
            }
        }

        private fun getColor(type: WeatherType): Int = when (type) {
            RAINY -> Color.GRAY
            SUNNY -> Color.GREEN
            SNOWY -> Color.BLUE
        }
    }
}
