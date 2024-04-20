package com.baims.network.di

import com.baims.network.utils.Network
import com.baims.utils.config.EnvironmentConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        environmentConfig: EnvironmentConfig
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(environmentConfig.getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(headerInterceptor)
        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        environmentConfig: EnvironmentConfig
    ): Interceptor =
        Interceptor { chain ->
            val originalRequest = chain.request()
            val url: HttpUrl = originalRequest.url.newBuilder()
                .addQueryParameter(Network.Queries.APP_ID, environmentConfig.getAppId())
                .build()
            val newRequest = originalRequest.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }

}