package com.ruizurraca.techtest.presentation.stations

import android.location.Geocoder
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.ruizurraca.techtest.R
import com.ruizurraca.techtest.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class StationsActivity : BaseActivity() {

    override val TAG = StationsActivity::class.java.name
    private var upperRightLatLon = LatLng(0.0, 0.0)
    private var lowerLeftLatLon = LatLng(0.0, 0.0)
    lateinit var geocoder: Geocoder

    private val stationsViewModel: StationsViewModel by viewModel()

    override fun layoutResID() = R.layout.activity_maps

    private lateinit var googleMap: GoogleMap

    private val onMapReadyCallback =
        OnMapReadyCallback { googleMap ->
            this@StationsActivity.googleMap = googleMap
            googleMap.setOnCameraIdleListener { manageNewMapPosition() }
        }

    @ExperimentalCoroutinesApi
    private fun manageNewMapPosition() {
        val citiesList = geocoder.getFromLocation(
            googleMap.cameraPosition.target.latitude,
            googleMap.cameraPosition.target.longitude,
            1
        )
        googleMap.projection.visibleRegion.latLngBounds.apply {
            upperRightLatLon = northeast
            lowerLeftLatLon = southwest
        }
        if (citiesList.isNotEmpty()) {
            citiesList.first().locality?.apply {
                stationsViewModel.getStations(
                    MarkCoordinates(
                        lowerLeftLatLon = lowerLeftLatLon,
                        upperRightLatLon = upperRightLatLon,
                        cityName = this.toLowerCase(Locale.getDefault())
                    )
                )
            }
        }

        with(stationsViewModel) {
            stationsData.observe(this@StationsActivity, Observer {
                logd("data")
            })
            messageData.observe(this@StationsActivity, Observer {
                logd("message")
            })
            showProgressbar.observe(this@StationsActivity, Observer { isVisible ->
                logd("progressBar")
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(onMapReadyCallback)
        geocoder = Geocoder(this)
    }
}