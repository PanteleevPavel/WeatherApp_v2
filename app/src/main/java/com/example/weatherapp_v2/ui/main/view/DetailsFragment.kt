package com.example.weatherapp_v2.ui.main.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp_v2.databinding.DetailFragmentBinding
import com.example.weatherapp_v2.ui.main.model.*

class DetailsFragment : Fragment() {

    companion object {

        const val BUNDLE_EXTRA = "city"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val localResultBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(RESULT_EXTRA)) {
                SUCCESS_RESULT -> {
                    intent.getParcelableExtra<WeatherDTO.FactDTO>(FACT_WEATHER_EXTRA)?.let {
                        displayWeather(it)
                    }
                }
                ERROR_EMPTY_DATA_RESULT -> {
                    Toast.makeText(context, "Error data load", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            localResultBroadcastReceiver, IntentFilter(
                DETAILS_INTENT_FILTER
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<City>(BUNDLE_EXTRA)?.let { city ->
            with(binding) {
                cityName.text = city.name
                cityCoordinates.text =
                    "Широта/Долгота: \n" + city.lat.toString() + " ; " + city.lon.toString()
                getWeather(city.lat, city.lon)
            }
        }
    }

    private fun getWeather(lat: Double, lon: Double) {
        binding.mainView.hide()
        binding.loadingLayout.show()

        requireActivity().startService(Intent(requireContext(), MainService::class.java).apply {
            putExtra(LATITUDE_EXTRA, lat).putExtra(LONGITUDE_EXTRA, lon)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayWeather(fact: WeatherDTO.FactDTO) {
        binding.mainView.show()
        binding.loadingLayout.hide()

        with(binding) {
            temperatureValue.text = "Температура: " + fact.temp.toString() + "°C"
            feelsLikeValue.text = "Ощущается как: " + fact.feels_like.toString() + "°C"
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(
            localResultBroadcastReceiver
        )
        super.onDestroy()
    }
}