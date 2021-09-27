package com.example.weatherapp_v2.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_v2.databinding.MainFragmentBinding
import com.example.weatherapp_v2.ui.main.model.City
import com.example.weatherapp_v2.ui.main.viewModel.AppState
import com.example.weatherapp_v2.ui.main.viewModel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MainFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MainFragmentAdapter(object : OnItemViewClickListener {
            @SuppressLint("ResourceType")
            override fun onItemViewClick(city: City) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, city)
                    manager.beginTransaction()
                        .add(
                            com.example.weatherapp_v2.R.layout.main_fragment,
                            DetailsFragment.newInstance(bundle)
                        )
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })
        binding.recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getCityFromLocalSource()
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.cityList = appState.cityData
                binding.textView.text = "Городов: " + appState.cityData.size.toString()
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                Toast.makeText(context, "ОШИБКА ПРИ ЗАГРУЗКЕ ДАННЫХ", Toast.LENGTH_LONG).show()
                viewModel.getCityFromLocalSource()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(city: City)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
