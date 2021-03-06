package com.example.weatherapp_v2.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.weatherapp_v2.R
import com.example.weatherapp_v2.databinding.DetailFragmentBinding
import com.example.weatherapp_v2.ui.main.model.City
import com.example.weatherapp_v2.ui.main.viewModel.AppState
import com.example.weatherapp_v2.ui.main.viewModel.DetailViewModel

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
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
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
        val city = arguments?.getParcelable(BUNDLE_EXTRA) ?: City()

        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }

        viewModel.getWeatherFromRemoteSource(city)
    }

    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Success -> {
                binding.mainView.show()
                binding.loadingLayout.hide()
                val city = appState.cityData.first()
                val weather = appState.cityData.first().weather
                with(binding) {
                    imageView.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                    cityName.text = city.name
                    cityCoordinates.text =
                        resources.getString(R.string.cityCoordinates, city.lat, city.lon)
                    condition.text = weather.condition
                    temperatureValue.text =
                        resources.getString(R.string.temperature, weather.temperature)
                    feelsLikeValue.text = resources.getString(R.string.feelsLike, weather.feelsLike)
                }
                viewModel.saveWeather(city)
            }
            is AppState.Loading -> {
                binding.loadingLayout.show()
                binding.mainView.hide()
            }
            is AppState.Error -> {
                binding.loadingLayout.show()
                Toast.makeText(context, "???????????? ?????? ???????????????? ????????????", Toast.LENGTH_LONG).show()
                viewModel.getWeatherFromRemoteSource(City())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}