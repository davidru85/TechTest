package com.ruizurraca.techtest.presentation.stations

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.ruizurraca.techtest.domain.model.ErrorModel
import com.ruizurraca.techtest.domain.model.Station
import com.ruizurraca.techtest.domain.usecase.GetStationsUseCase
import com.ruizurraca.techtest.domain.usecase.base.UseCaseResponse
import com.ruizurraca.techtest.presentation.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class StationsViewModel constructor(private val getStationsUseCase: GetStationsUseCase) :
    BaseViewModel() {

    val stationsData = MutableLiveData<List<Station>>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()

    @ExperimentalCoroutinesApi
    fun getStations(markCoordinates: MarkCoordinates) {
        showProgressbar.value = true
        getStationsUseCase.invoke(markCoordinates, object : UseCaseResponse<List<Station>> {
            override fun onSuccess(result: List<Station>) {
                Log.i(TAG, "result: $result")
                stationsData.value = result
                showProgressbar.value = false
            }

            override fun onError(errorModel: ErrorModel?) {
                messageData.value = errorModel?.message
                showProgressbar.value = false
            }
        })
    }

    companion object {
        private val TAG = StationsViewModel::class.java.name
    }
}

data class MarkCoordinates(val lowerLeftLatLon: LatLng, val upperRightLatLon: LatLng)