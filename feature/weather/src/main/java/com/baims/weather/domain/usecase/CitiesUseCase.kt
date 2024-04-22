package com.baims.weather.domain.usecase

import com.baims.weather.data.response.CitiesResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */


interface CitiesUseCase {

    suspend fun getCities(): Flow<CitiesResponse>

}