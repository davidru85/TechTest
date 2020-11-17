package com.ruizurraca.techtest.di

import com.ruizurraca.techtest.presentation.stations.StationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModules = module {
    viewModel { StationsViewModel(get()) }
    single { createGetStationsUseCase(get(), createApiErrorHandle()) }
    single { createStationsRepository(get()) }
}