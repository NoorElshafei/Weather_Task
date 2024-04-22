package com.baims.weather.domain.repository

import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.response.City
import com.baims.weather.data.response.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getCities(): Flow<CitiesResponse>
    suspend fun getForecast(city:City?): Flow<ForecastResponse>
}
