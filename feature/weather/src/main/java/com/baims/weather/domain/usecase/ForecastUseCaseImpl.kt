package com.baims.weather.domain.usecase

import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.response.City
import com.baims.weather.data.response.ForecastResponse
import com.baims.weather.domain.repository.WeatherRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ForecastUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : ForecastUseCase {
    override suspend fun getForecast(city: City?): Flow<ForecastResponse> = repository.getForecast(city)
}
