package com.ruizurraca.techtest.domain.usecase

import com.ruizurraca.techtest.domain.exception.ApiErrorHandle
import com.ruizurraca.techtest.domain.model.Station
import com.ruizurraca.techtest.domain.repository.StationsRepository
import com.ruizurraca.techtest.domain.usecase.base.UseCase

class GetStationsUseCase constructor(
    private val stationsRepository: StationsRepository,
    apiErrorHandle: ApiErrorHandle
) : UseCase<List<Station>, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): List<Station> {
        return stationsRepository.getStations()
    }

}