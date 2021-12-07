package com.example.weatherapp_v2.ui.main.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp_v2.R
import com.example.weatherapp_v2.databinding.MainActivityBinding
import java.io.IOException

private const val REFRESH_PERIOD = 6000L
private const val MINIMAL_DISTANCE = 100f

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
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.idHistory -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, HistoryFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.location -> {
                permissionGeoResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val permissionGeoResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> getLocation()

                !ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    Toast.makeText(
                        this,
                        "Go to app settings and enable permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(this, ":-(", Toast.LENGTH_LONG).show()
            }
        }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.getProvider(LocationManager.GPS_PROVIDER)?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            getAddressByLocation(location)
                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {
                            //super.onStatusChanged(provider, status, extras)
                            //Нельзя вызывать super у этого метода т.к. он deprecated и уже не работает.
                            //Поэтому если вызвать супер приложение будет падать.
                        }

                        override fun onProviderEnabled(provider: String) {}
                        override fun onProviderDisabled(provider: String) {}

                    }
                )
            }
        } else {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                getAddressByLocation(it)
            } ?: Toast.makeText(this, "Не удалось определить текущую локацию", Toast.LENGTH_LONG)
                .show()

        }
    }

    private fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(this)

        Thread {
            try {
                val address = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                binding.container.post {
                    AlertDialog.Builder(this)
                        .setMessage(address[0].getAddressLine(0))
                        .setCancelable(true)
                        .show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}