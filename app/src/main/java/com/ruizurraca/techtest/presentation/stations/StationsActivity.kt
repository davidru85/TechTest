package com.ruizurraca.techtest.presentation.stations

import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.ruizurraca.techtest.R
import com.ruizurraca.techtest.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StationsActivity : BaseActivity() {

    override val TAG = StationsActivity::class.java.name
    private var upperRightLatLon = LatLng(0.0, 0.0)
    private var lowerLeftLatLon = LatLng(0.0, 0.0)

    private val stationsViewModel: StationsViewModel by viewModel()

    override fun layoutResID() = R.layout.activity_maps

    private lateinit var googleMap: GoogleMap

    private val onMapReadyCallback =
        OnMapReadyCallback { googleMap ->
            this@StationsActivity.googleMap = googleMap
            googleMap.setOnCameraIdleListener { manageNewMapPosition() }
        }

    private fun manageNewMapPosition() {
        upperRightLatLon = googleMap.projection.visibleRegion.latLngBounds.northeast
        lowerLeftLatLon = googleMap.projection.visibleRegion.latLngBounds.southwest
        stationsViewModel.getStations(
            MarkCoordinates(
                lowerLeftLatLon = lowerLeftLatLon,
                upperRightLatLon = upperRightLatLon
            )
        )

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
    }
}