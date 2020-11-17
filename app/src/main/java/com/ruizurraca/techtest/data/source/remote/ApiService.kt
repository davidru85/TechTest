package com.ruizurraca.techtest.data.source.remote

import com.ruizurraca.techtest.domain.model.Station
import retrofit2.http.GET

interface ApiService {

    @GET("/tripplan/api/v1/routers/lisboa/resources?lowerLeftLatLon=38.711046,-9.160096&upperRightLatLon=38.739429,-9.137115")
    suspend fun getStations(): List<Station>
}