package com.baims.weather.presentation.weather_forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baims.utils.extensions.catchError
import com.baims.utils.states.DataState
import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.response.City
import com.baims.weather.data.response.ForecastResponse
import com.baims.weather.domain.enums.ForecastValidation
import com.baims.weather.domain.usecase.CitiesUseCase
import com.baims.weather.domain.usecase.ForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val citiesUseCase: CitiesUseCase,
    private val forecastUseCase: ForecastUseCase,
) : ViewModel() {

    private val _citiesDataState: MutableStateFlow<DataState<CitiesResponse>> =
        MutableStateFlow(DataState.Loading)
    val citiesDataState: StateFlow<DataState<CitiesResponse>> get() = _citiesDataState

    private val _forecastDataState: MutableStateFlow<DataState<ForecastResponse>> =
        MutableStateFlow(DataState.None)
    val forecastDataState: StateFlow<DataState<ForecastResponse>> get() = _forecastDataState

    private val validationForecastLiveData = MutableLiveData<Int>()
    val validationForecast: LiveData<Int> get() = validationForecastLiveData

    fun getCities() {
        viewModelScope.launch {
            citiesUseCase.getCities()
                .catchError { _citiesDataState.emit(DataState.Failure(it)) }
                .collect { _citiesDataState.emit(DataState.Success(it)) }
        }
    }

    fun getForecast(city: City?) {
        if (validateForecast(city)) {
            viewModelScope.launch {
                forecastUseCase.getForecast(city)
                    .onStart { _forecastDataState.emit(DataState.Loading) }
                    .catchError { _forecastDataState.emit(DataState.Failure(it)) }
                    .collect { _forecastDataState.emit(DataState.Success(it)) }
            }
        }
    }

    private fun validateForecast(
        city: City?
    ): Boolean {
        return if (city?.lat == null || city.lon == null) {
            validationForecastLiveData.value = ForecastValidation.EMPTY_FORECAST.value
            false
        } else {
            true
        }

    }
}
