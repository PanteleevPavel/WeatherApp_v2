package com.example.weatherapp_v2.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp_v2.R
import com.example.weatherapp_v2.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.action_container, MainFragment.newInstance())
                .commitNow()
        }
    }
}