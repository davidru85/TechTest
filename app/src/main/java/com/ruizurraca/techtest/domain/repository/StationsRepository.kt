package com.ruizurraca.techtest.domain.repository

import com.ruizurraca.techtest.domain.model.Station

interface StationsRepository {
    suspend fun getStations(): List<Station>
}