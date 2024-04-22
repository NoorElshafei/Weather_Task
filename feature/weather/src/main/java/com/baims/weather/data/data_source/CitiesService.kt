package com.baims.weather.data.data_source

import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.utils.EndPoints
import retrofit2.http.GET

interface CitiesService {

    @GET(EndPoints.Cities)
    suspend fun getCities(): CitiesResponse
}
