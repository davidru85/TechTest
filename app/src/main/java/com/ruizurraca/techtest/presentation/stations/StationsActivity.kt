package com.ruizurraca.techtest.presentation.stations

import android.location.Geocoder
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ruizurraca.techtest.R
import com.ruizurraca.techtest.domain.model.Station
import com.ruizurraca.techtest.presentation.base.BaseActivity
import com.ruizurraca.techtest.utils.extensions.gone
import com.ruizurraca.techtest.utils.extensions.visible
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

const val MIN_ZOOM = 13
const val MAX_PINS_TO_SHOW = 300

class StationsActivity : BaseActivity() {

    private var upperRightLatLon = LatLng(0.0, 0.0)
    private var lowerLeftLatLon = LatLng(0.0, 0.0)
    lateinit var geocoder: Geocoder

    private val stationsViewModel: StationsViewModel by viewModel()

    override fun layoutResID() = R.layout.activity_maps

    private lateinit var googleMap: GoogleMap

    @ExperimentalCoroutinesApi
    private val onMapReadyCallback =
        OnMapReadyCallback { googleMap ->
            this@StationsActivity.googleMap = googleMap
            googleMap.setOnCameraIdleListener { manageNewMapPosition() }
            googleMap.setOnMarkerClickListener {
                it.showInfoWindow(); // show info window
                true
            }
        }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(onMapReadyCallback)
        geocoder = Geocoder(this)

        observeLiveData()
    }

    private fun observeLiveData() {
        with(stationsViewModel) {
            stationsData.observe(this@StationsActivity, {
                populateMap(it)
            })
            messageData.observe(this@StationsActivity, {
                logd("message")
            })
            showProgressbar.observe(this@StationsActivity, { isVisible ->
                pb_loading.apply {
                    if (isVisible) {
                        visible()
                    } else {
                        gone()
                    }
                }
            })
        }
    }

    private fun populateMap(stationsList: List<Station>) {
        if (stationsList.size < MAX_PINS_TO_SHOW) {
            val options = MarkerOptions()
            logd(stationsList.toString())
            stationsList.forEach {
                if (it.isValid()) {
                    options.position(LatLng(it.coordinateY ?: 0.0, it.coordinateX ?: 0.0))
                    options.title(it.name)
                    googleMap.addMarker(options)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun manageNewMapPosition() {
        googleMap.clear()
        if (googleMap.cameraPosition.zoom > MIN_ZOOM) {
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
        }
    }
}