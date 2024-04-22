package com.baims.weather.presentation.weather_forecast.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.baims.weather.data.response.ForecastDetails
import com.baims.weather.presentation.diffCallback.ForecastDiffCallback
import javax.inject.Inject

class ForecastAdapter @Inject constructor() :
    ListAdapter<ForecastDetails, ForecastHolder>(ForecastDiffCallback()) {

        override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder =
        ForecastHolder.from(parent)
}
