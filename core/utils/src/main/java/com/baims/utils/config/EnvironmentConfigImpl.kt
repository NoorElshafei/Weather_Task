package com.baims.utils.config


import com.baims.core.utils.BuildConfig
import javax.inject.Inject

class EnvironmentConfigImpl @Inject constructor() : EnvironmentConfig {

    override fun getBaseUrl(): String = BuildConfig.BASE_URL
    override fun getAppId(): String = BuildConfig.APP_ID

}
