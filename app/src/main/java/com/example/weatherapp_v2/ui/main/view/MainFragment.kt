package com.example.weatherapp_v2.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
