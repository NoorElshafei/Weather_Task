package com.baims.weather.domain.usecase

import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.domain.repository.WeatherRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CitiesUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : CitiesUseCase {
    override suspend fun getCities(): Flow<CitiesResponse> = repository.getCities()
}
