package com.baims.weather.presentation.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.baims.weather.data.response.ForecastDetails

class ForecastDiffCallback : DiffUtil.ItemCallback<ForecastDetails>() {
    override fun areItemsTheSame(oldItem: ForecastDetails, newItem: ForecastDetails) =
        oldItem.itemId == newItem.itemId

    override fun areContentsTheSame(oldItem: ForecastDetails, newItem: ForecastDetails) =
        oldItem == newItem
}
