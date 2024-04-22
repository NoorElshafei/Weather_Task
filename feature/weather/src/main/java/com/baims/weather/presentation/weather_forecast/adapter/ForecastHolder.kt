package com.baims.weather.presentation.weather_forecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.baims.feature.weather.databinding.ItemForecastBinding
import com.baims.uitest.TestTags
import com.baims.uitest.testTag
import com.baims.weather.data.response.ForecastDetails

class ForecastHolder(
    private val binding: ItemForecastBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecastDetails: ForecastDetails) {
       val celsiusDegree = forecastDetails.main?.temp?.minus(273.15)
        binding.weatherDescription.text = forecastDetails.weather?.get(0)?.description.toString()
        binding.CurrentTemp.text = String.format("%.2f", celsiusDegree)
        binding.txtWind.text= forecastDetails.wind?.speed.toString()
        binding.txtPressure.text= forecastDetails.main?.pressure.toString()
        binding.txtHumidity.text=forecastDetails.main?.humidity.toString()

    }

    companion object {
        fun from(
            parent: ViewGroup
        ): ForecastHolder {
            val binding = ItemForecastBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            binding.itemForecast.testTag(TestTags.ForecastFragment.RV_ITEM)
            return ForecastHolder(binding)
        }
    }
}
