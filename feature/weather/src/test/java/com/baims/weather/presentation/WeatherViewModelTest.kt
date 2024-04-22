package com.baims.weather.presentation

import app.cash.turbine.test
import com.baims.utils.states.DataState
import com.baims.weather.data.response.City
import com.baims.weather.data.utils.Mocks
import com.baims.weather.domain.usecase.CitiesUseCase
import com.baims.weather.domain.usecase.ForecastUseCase
import com.baims.weather.presentation.weather_forecast.WeatherViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private var citiesUseCase: CitiesUseCase = mockkClass(CitiesUseCase::class)
    private var forecastUseCase: ForecastUseCase = mockkClass(ForecastUseCase::class)

    private val expectedForecastSuccessResult = Mocks.forecast
    private val expectedCitiesSuccessResult = Mocks.cities
    private val expectedForecastFailureResult = Mocks.exceptionForecast
    private val expectedCitiesFailureResult = Mocks.exceptionCities


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test getCities with success response then return success`() {
        runTest {
            coEvery { citiesUseCase.getCities() } answers { flow { emit(expectedCitiesSuccessResult) } }
            val viewModel = WeatherViewModel(citiesUseCase,forecastUseCase)
            viewModel.getCities()
            viewModel.citiesDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Success(Mocks.cities), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) { citiesUseCase.getCities() }
        }
    }

    @Test
    fun `test getForecast with success response then return success`() {
        runTest {
            coEvery { forecastUseCase.getForecast(Mocks.city) } answers { flow { emit(expectedForecastSuccessResult) } }
            val viewModel = WeatherViewModel(citiesUseCase,forecastUseCase)
            viewModel.getForecast(Mocks.city)
            viewModel.forecastDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Success(Mocks.forecast), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { forecastUseCase.getForecast(Mocks.city) }
        }
    }

    @Test
    fun `test getCities with failure response then return error`() {
        runTest {
            coEvery { citiesUseCase.getCities() } answers { flow { throw expectedCitiesFailureResult } }
            val viewModel = WeatherViewModel(citiesUseCase,forecastUseCase)
            viewModel.getCities()
            viewModel.citiesDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Failure(expectedCitiesFailureResult), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { citiesUseCase.getCities() }
        }
    }

    @Test
    fun `test getForecast with failure response then return error`() {
        runTest {
            coEvery { forecastUseCase.getForecast(Mocks.city) } answers { flow { throw expectedForecastFailureResult } }
            val viewModel = WeatherViewModel(citiesUseCase,forecastUseCase)
            viewModel.getForecast(Mocks.city)
            viewModel.forecastDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Failure(expectedForecastFailureResult), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { forecastUseCase.getForecast(Mocks.city) }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
