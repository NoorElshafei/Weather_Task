package com.baims.weather.data.response
import com.google.gson.annotations.SerializedName


/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */


data class CitiesResponse(
    @SerializedName("cities")
    val cities: List<City?>? = null
)

data class City(
    @SerializedName("cityNameAr")
    val cityNameAr: String? = null,
    @SerializedName("cityNameEn")
    val cityNameEn: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null
)