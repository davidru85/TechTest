package com.ruizurraca.techtest.domain.repository

import com.ruizurraca.techtest.domain.model.Station
import com.ruizurraca.techtest.presentation.stations.MarkCoordinates

interface StationsRepository {
    suspend fun getStations(markCoordinates: MarkCoordinates?): List<Station>
}