package com.baims.weather.domain


import com.baims.weather.data.response.ForecastResponse
import com.baims.weather.data.utils.Mocks
import com.baims.weather.domain.repository.WeatherRepository
import com.baims.weather.domain.usecase.ForecastUseCase
import com.baims.weather.domain.usecase.ForecastUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import io.mockk.unmockkAll
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForeCastUseCaseTest {

    private lateinit var useCase: ForecastUseCase

    private val repository: WeatherRepository = mockkClass(WeatherRepository::class)

    private val expectedSuccessResult = Mocks.forecast
    private val expectedFailureResult = Mocks.exceptionForecast

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ForecastUseCaseImpl(repository)
    }

    @Test
    fun `test getForecast with success response then return success`() = runTest {
        coEvery { repository.getForecast(Mocks.city) } answers { flow { emit(expectedSuccessResult) } }

        val response = useCase.getForecast(Mocks.city)

        var success: ForecastResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedSuccessResult.list?.size, success!!.list?.size)
        coVerify(exactly = 1) { repository.getForecast(Mocks.city) }
    }

    @Test
    fun `test getForecast with failure response then return error`() = runTest {
        coEvery { repository.getForecast(Mocks.city) } answers { flow { throw expectedFailureResult } }
        val response = useCase.getForecast(Mocks.city)

        var success: ForecastResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedFailureResult.message, error!!.message)
        coVerify(exactly = 1) { repository.getForecast(Mocks.city) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
