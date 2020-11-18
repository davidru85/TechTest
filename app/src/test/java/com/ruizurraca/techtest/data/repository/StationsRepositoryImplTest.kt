package com.ruizurraca.techtest.data.repository

import com.google.android.gms.maps.model.LatLng
import com.ruizurraca.techtest.domain.model.Station
import com.ruizurraca.techtest.presentation.stations.MarkCoordinates
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class StationsRepositoryImplTest {

    @MockK
    lateinit var stationsRepository: StationsRepositoryImpl

    private val markCoordinates = MarkCoordinates(
        LatLng(40.41453909290702, 3.7185559049248695),
        LatLng(40.4742155150846, -3.669030591845513),
        "Madrid"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this) //for initialization
    }

    @Test
    fun getBreedsData() = runBlocking {
        val stationsList = mockk<List<Station>>()

        every {
            runBlocking {
                stationsRepository.getStations(markCoordinates)
            }
        } returns (stationsList)

        val result = stationsRepository.getStations(markCoordinates)
        MatcherAssert.assertThat(
            "Received result [$result] & mocked [$stationsList] must be matches on each other!",
            result,
            CoreMatchers.`is`(stationsList)
        )
    }
}