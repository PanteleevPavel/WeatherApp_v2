package com.example.weatherapp_v2.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp_v2.R
import com.example.weatherapp_v2.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, MainFragment.newInstance())
                .commitNow()
        }
    }
}