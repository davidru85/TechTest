package com.ruizurraca.techtest.data.repository

import com.ruizurraca.techtest.data.source.remote.ApiService
import com.ruizurraca.techtest.domain.repository.StationsRepository
import com.ruizurraca.techtest.presentation.stations.MarkCoordinates

class StationsRepositoryImpl(private val apiService: ApiService) : StationsRepository {
    override suspend fun getStations(markCoordinates: MarkCoordinates?) =
        apiService.getStations(lowerLeftLatLon = "${markCoordinates?.lowerLeftLatLon?.latitude},${markCoordinates?.lowerLeftLatLon?.longitude}",
            upperRightLatLon = "${markCoordinates?.upperRightLatLon?.latitude},${markCoordinates?.upperRightLatLon?.longitude}")
}
