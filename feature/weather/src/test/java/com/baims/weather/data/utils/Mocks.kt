package com.baims.weather.data.utils

import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.response.City
import com.baims.weather.data.response.ForecastDetails
import com.baims.weather.data.response.ForecastResponse
import com.baims.weather.data.response.Main
import com.baims.weather.data.response.Weather
import com.baims.weather.data.response.Wind

object Mocks {

    val city = City(
        cityNameAr = "cairo",
        cityNameEn = "القاهرة",
        id = 1,
        lat = 30.0444,
        lon = 31.2357
    )
    val cities = CitiesResponse(
        cities = listOf(
            city
        )
    )

    val forecast = ForecastResponse(
        list = listOf(
            ForecastDetails(
                main = Main(
                    humidity = 7,
                    pressure = 1016,
                    temp = 305.48
                    ),
                weather = listOf(Weather(description = "clear sky")),
                wind = Wind(speed = 6.5)
            )
        )
    )


    val exceptionCities = Throwable("Can't get cities, please try again later.")
    val exceptionForecast = Throwable("Can't get forecast, please try again later.")

}
