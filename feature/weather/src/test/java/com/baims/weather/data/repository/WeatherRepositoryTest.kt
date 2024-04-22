package com.baims.weather.data.repository


import com.baims.core.utils.BuildConfig
import com.baims.weather.data.data_source.CitiesService
import com.baims.weather.data.data_source.ForecastService
import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.response.ForecastResponse
import com.baims.weather.data.utils.Mocks
import com.baims.weather.domain.repository.WeatherRepository
import com.baims.weather.domain.repository.WeatherRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import io.mockk.unmockkAll
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: WeatherRepository

    private val dataSourceCities: CitiesService = mockkClass(CitiesService::class)
    private val dataSourceForecast: ForecastService = mockkClass(ForecastService::class)

    private val expectedForecastSuccessResult = Mocks.forecast
    private val expectedCitiesSuccessResult = Mocks.cities
    private val expectedForecastFailureResult = Mocks.exceptionForecast
    private val expectedCitiesFailureResult = Mocks.exceptionCities

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        repository = WeatherRepositoryImpl(dataSourceCities,dataSourceForecast, testDispatcher)
    }

    @Test
    fun `test getCities with success response then return success`() = runTest {
        coEvery { dataSourceCities.getCities() } returns expectedCitiesSuccessResult

        val response = repository.getCities()

        var success: CitiesResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedCitiesSuccessResult.cities?.size, success!!.cities?.size)
        coVerify(exactly = 1) { dataSourceCities.getCities() }
    }

    @Test
    fun `test getForecast with success response then return success`() = runTest {
        coEvery { dataSourceForecast.getForecast(
            any(),
            any(),
            any()) } returns expectedForecastSuccessResult

        val response = repository.getForecast(Mocks.city)

        var success: ForecastResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedForecastSuccessResult.list?.size, success!!.list?.size)
        coVerify(exactly = 1) { dataSourceForecast.getForecast(
            any(),
            any(),
            any()
        ) }
    }

    @Test
    fun `test getCities with failure response then return error`() = runTest {
        coEvery { dataSourceCities.getCities() } throws expectedCitiesFailureResult

        val response = repository.getCities()

        var success: CitiesResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedCitiesFailureResult.message, error!!.message)
        coVerify(exactly = 1) { dataSourceCities.getCities() }
    }

    @Test
    fun `test getForecast with failure response then return error`() = runTest {
        coEvery { dataSourceForecast.getForecast(
            any(),
            any(),
            any()) } throws expectedForecastFailureResult

        val response = repository.getForecast(Mocks.city)

        var success: ForecastResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedForecastFailureResult.message, error!!.message)
        coVerify(exactly = 1) { dataSourceForecast.getForecast(
            any(),
            any(),
            any()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
