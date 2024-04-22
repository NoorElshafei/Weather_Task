package com.baims.weather.domain.usecase

import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.response.City
import com.baims.weather.data.response.ForecastResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */


interface ForecastUseCase {

    suspend fun getForecast(city: City?): Flow<ForecastResponse>

}