package com.baims.utils.config


import com.baims.core.utils.BuildConfig
import javax.inject.Inject

class EnvironmentConfigImpl @Inject constructor() : EnvironmentConfig {

    override fun getBaseUrlForecast(): String = BuildConfig.BASE_URL_FORECAST
    override fun getBaseUrlCities(): String = BuildConfig.BASE_URL_CITES
    override fun getAppId(): String = BuildConfig.APP_ID

}
