package com.baims.weather.di

import com.baims.network.di.CitiesApi
import com.baims.network.di.ForecastApi
import com.baims.weather.data.data_source.CitiesService
import com.baims.weather.data.data_source.ForecastService
import com.baims.weather.domain.repository.WeatherRepository
import com.baims.weather.domain.repository.WeatherRepositoryImpl
import com.baims.weather.domain.usecase.CitiesUseCase
import com.baims.weather.domain.usecase.CitiesUseCaseImpl
import com.baims.weather.domain.usecase.ForecastUseCase
import com.baims.weather.domain.usecase.ForecastUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideCitiesService(
        @CitiesApi retrofit: Retrofit
    ): CitiesService = retrofit.create(CitiesService::class.java)

    @Provides
    @Singleton
    fun provideForecastService(
        @ForecastApi retrofit: Retrofit
    ): ForecastService = retrofit.create(ForecastService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingWeatherModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindCitiesUseCase(
        citiesUseCaseImpl: CitiesUseCaseImpl
    ): CitiesUseCase

    @Binds
    @Singleton
    abstract fun bindForecastUseCase(
        forecastUseCaseImpl: ForecastUseCaseImpl
    ): ForecastUseCase

}
