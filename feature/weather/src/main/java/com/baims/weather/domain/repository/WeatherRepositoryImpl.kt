package com.baims.weather.domain.repository

import com.baims.network.di.ForecastApi
import com.baims.network.di.IoDispatcher
import com.baims.weather.data.data_source.CitiesService
import com.baims.weather.data.data_source.ForecastService
import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.response.City
import com.baims.weather.data.response.ForecastResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiCities: CitiesService,
    private val apiForecast: ForecastService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun getCities(): Flow<CitiesResponse> = flow {
        val cities = apiCities.getCities()
        emit(cities)
    }.flowOn(dispatcher)

    override suspend fun getForecast(city: City?): Flow<ForecastResponse> = flow {
        val cities = apiForecast.getForecast(city?.lat.toString(), city?.lat.toString())
        emit(cities)
    }.flowOn(dispatcher)
}