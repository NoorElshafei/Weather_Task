package com.baims.network.data.entities

import com.google.gson.annotations.SerializedName

class ApiResponse<T>(@SerializedName("results") val results: List<T>)
