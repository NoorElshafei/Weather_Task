package com.baims.weather.data.response
import com.baims.ui.models.BaseModel
import com.google.gson.annotations.SerializedName
import java.util.Random


/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */


data class ForecastResponse(
    @SerializedName("list")
    val list: List<ForecastDetails?>? = null
)

data class ForecastDetails(
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weather: List<Weather?>? = null,
    @SerializedName("wind")
    val wind: Wind? = null
):BaseModel()


data class Main(
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("temp")
    val temp: Double? = null
)


data class Weather(
    @SerializedName("description")
    val description: String? = null
)

data class Wind(
    @SerializedName("speed")
    val speed: Double? = null
)