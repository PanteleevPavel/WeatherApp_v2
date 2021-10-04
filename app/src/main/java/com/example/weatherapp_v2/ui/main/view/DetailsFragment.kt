package com.example.weatherapp_v2.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherapp_v2.databinding.DetailFragmentBinding
import com.example.weatherapp_v2.ui.main.model.City
import com.example.weatherapp_v2.ui.main.model.WeatherDTO
import com.example.weatherapp_v2.ui.main.model.WeatherLoader

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
            }

            WeatherLoader(
                city.lat,
                city.lon,
                object : WeatherLoader.WeatherLoaderListener {
                    override fun onLoaded(weatherDTO: WeatherDTO) {
                        requireActivity().runOnUiThread {
                            displayWeather(weatherDTO)
                        }
                    }

                    override fun onFailed(throwable: Throwable) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(
                                requireContext(),
                                throwable.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            ).goToInternet()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayWeather(weather: WeatherDTO) {
        with(binding) {
            temperatureValue.text = "Температура: " + weather.fact?.temp.toString() + "°C"
            feelsLikeValue.text = "Ощущается как: " + weather.fact?.feels_like.toString() + "°C"
        }
    }
}