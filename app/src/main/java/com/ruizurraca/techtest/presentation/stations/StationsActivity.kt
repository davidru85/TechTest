package com.ruizurraca.techtest.presentation.stations

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ruizurraca.techtest.R
import com.ruizurraca.techtest.presentation.base.BaseActivity

class StationsActivity : BaseActivity() {

    override fun layoutResID() = R.layout.activity_maps

    private lateinit var googleMap: GoogleMap

    private val onMapReadyCallback = object : OnMapReadyCallback {
        override fun onMapReady(googleMap: GoogleMap) {
            this@StationsActivity.googleMap = googleMap

            // Add a marker in Sydney and move the camera
            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(onMapReadyCallback)
    }
}