package com.example.weatherapp_v2.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp_v2.databinding.ReciclerFragmentBinding
import com.example.weatherapp_v2.ui.main.model.City

class DetailsFragment : Fragment() {

    private var _binding: ReciclerFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReciclerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = arguments?.getParcelable<City>(BUNDLE_EXTRA)
        if (city != null) {
            binding.town.text = city.name
            binding.temp.text = city.weather.temperature.toString()
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "city"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}