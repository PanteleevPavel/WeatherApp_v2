package com.example.weatherapp_v2.ui.main.viewModel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_v2.R
import com.example.weatherapp_v2.ui.main.model.database.HistoryEntity
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<HistoryEntity> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<HistoryEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SimpleDateFormat")
        fun bind(data: HistoryEntity) {
            itemView.apply {
                findViewById<TextView>(R.id.idHistory).text = data.id.toString()
                findViewById<TextView>(R.id.city).text = data.city
                findViewById<TextView>(R.id.temperature).text =
                    resources.getString(R.string.historyTemperatureText, data.temperature)
                findViewById<TextView>(R.id.dateTime).text =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(data.timestamp))
            }
        }
    }
}