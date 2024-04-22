package com.baims.utils.config

interface EnvironmentConfig {

    fun getBaseUrlForecast(): String
    fun getBaseUrlCities(): String
    fun getAppId(): String
}
