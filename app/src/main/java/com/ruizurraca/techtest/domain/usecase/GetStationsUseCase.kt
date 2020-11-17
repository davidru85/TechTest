package com.ruizurraca.techtest.domain.usecase

import com.ruizurraca.techtest.domain.exception.ApiErrorHandle
import com.ruizurraca.techtest.domain.model.Station
import com.ruizurraca.techtest.domain.repository.StationsRepository
import com.ruizurraca.techtest.domain.usecase.base.UseCase
import com.ruizurraca.techtest.presentation.stations.MarkCoordinates

class GetStationsUseCase constructor(
    private val stationsRepository: StationsRepository,
    apiErrorHandle: ApiErrorHandle
) : UseCase<List<Station>, MarkCoordinates?>(apiErrorHandle) {

    override suspend fun run(markCoordinates: MarkCoordinates?): List<Station> {
        return stationsRepository.getStations(markCoordinates)
    }

}