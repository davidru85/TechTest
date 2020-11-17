package com.ruizurraca.techtest.data.source.remote

import com.ruizurraca.techtest.domain.model.Station
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/tripplan/api/v1/routers/lisboa/resources")
    suspend fun getStations(
        @Query("lowerLeftLatLon") lowerLeftLatLon: String? = "0.0,0.0",
        @Query("upperRightLatLon") upperRightLatLon: String? = "0.0,0.0"
    ): List<Station>
}