package com.ruizurraca.techtest.di

import com.google.gson.Gson
import com.ruizurraca.techtest.BuildConfig
import com.ruizurraca.techtest.data.repository.StationsRepositoryImpl
import com.ruizurraca.techtest.data.source.remote.ApiService
import com.ruizurraca.techtest.domain.exception.ApiErrorHandle
import com.ruizurraca.techtest.domain.repository.StationsRepository
import com.ruizurraca.techtest.domain.usecase.GetStationsUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

    single { createGsonConverterFactory() }

    single { createGson() }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


fun createGson(): Gson {
    return Gson()
}

fun createGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}


fun createApiErrorHandle(): ApiErrorHandle {
    return ApiErrorHandle()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createStationsRepository(apiService: ApiService): StationsRepository {
    return StationsRepositoryImpl(apiService)
}

fun createGetStationsUseCase(
    stationsRepository: StationsRepository,
    apiErrorHandle: ApiErrorHandle
): GetStationsUseCase {
    return GetStationsUseCase(stationsRepository, apiErrorHandle)
}