package com.baims.weather.data.data_source

import com.baims.core.utils.BuildConfig
import com.baims.weather.data.response.ForecastResponse
import com.baims.weather.data.utils.EndPoints
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {

    @GET(EndPoints.Forecast)
    suspend fun getForecast(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") appId: String = BuildConfig.APP_ID
    ): ForecastResponse
}
