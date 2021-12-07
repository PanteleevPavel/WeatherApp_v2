package com.example.weatherapp_v2.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp_v2.R
import com.example.weatherapp_v2.databinding.MainFragmentBinding
import com.example.weatherapp_v2.ui.main.model.City
import com.example.weatherapp_v2.ui.main.viewModel.AppState
import com.example.weatherapp_v2.ui.main.viewModel.MainViewModel

class MainFragment : Fragment() {

    interface OnItemViewClickListener {
        fun onItemViewClick(city: City)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter: MainFragmentAdapter by lazy {
        MainFragmentAdapter(object : OnItemViewClickListener {
            @SuppressLint("ResourceType")
            override fun onItemViewClick(city: City) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(
                            R.id.container,
                            DetailsFragment.newInstance(Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, city)
                            })
                        )
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getCityFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.hide()
                adapter.cityList = appState.cityData
                binding.textView.text =
                    resources.getString(R.string.cityNumbers, appState.cityData.size)

                addFilter(appState.cityData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.show()
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.hide()
                Toast.makeText(context, "ОШИБКА ПРИ ЗАГРУЗКЕ ДАННЫХ", Toast.LENGTH_LONG).show()
                viewModel.getCityFromLocalSource()
            }
        }
    }

    private fun addFilter(cityList: List<City>) {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                applyFilter(s.toString(), cityList)
            }
        }
        )
    }

    fun applyFilter(s: String, cityList: List<City>) {

        val filteredCityList: MutableList<City> = mutableListOf()

        for (city in cityList) {
            if (city.name?.contains(other = s, ignoreCase = true) == true) {
                filteredCityList.add(city)
            }
        }

        adapter.cityList = filteredCityList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
