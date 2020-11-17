package com.ruizurraca.techtest.data.repository

import com.ruizurraca.techtest.data.source.remote.ApiService
import com.ruizurraca.techtest.domain.repository.StationsRepository

class StationsRepositoryImpl(private val apiService: ApiService) : StationsRepository {
    override suspend fun getStations() = apiService.getStations()
}
