package com.example.locationdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.locationdemo.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val TAG = "MainActivity"
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        geocoder = Geocoder(this,Locale.TRADITIONAL_CHINESE)

        getLastLocation()
    }

    private fun getLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),10101)
        }

        val lastLocation = fusedLocationProviderClient.lastLocation

        lastLocation.addOnSuccessListener {
            binding.tvLatitude.text = it.latitude.toString()
            Log.d(TAG,"getLastLocation: ${it.latitude}")
            binding.tvLongitude.text = it.longitude.toString()
            Log.d(TAG,"getLastLocation: ${it.longitude}")

            val address = geocoder.getFromLocation(it.latitude,it.longitude,1)

            binding.tvAddress.text = address[0].getAddressLine(0)
            Log.d(TAG,"LastLocation: ${address[0].getAddressLine(0)}")
            Log.d(TAG,"LastLocation: ${address[0].locality}")
        }

        lastLocation.addOnFailureListener {
            Log.d(TAG,"Failed")
        }
    }

}