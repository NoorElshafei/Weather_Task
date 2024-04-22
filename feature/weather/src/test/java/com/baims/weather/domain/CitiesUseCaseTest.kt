package com.baims.weather.domain


import com.baims.weather.data.response.CitiesResponse
import com.baims.weather.data.utils.Mocks
import com.baims.weather.domain.repository.WeatherRepository
import com.baims.weather.domain.usecase.CitiesUseCase
import com.baims.weather.domain.usecase.CitiesUseCaseImpl
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
class CitiesUseCaseTest {

    private lateinit var useCase: CitiesUseCase

    private val repository: WeatherRepository = mockkClass(WeatherRepository::class)

    private val expectedSuccessResult = Mocks.cities
    private val expectedFailureResult = Mocks.exceptionCities

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CitiesUseCaseImpl(repository)
    }

    @Test
    fun `test getCities with success response then return success`() = runTest {
        coEvery { repository.getCities() } answers { flow { emit(expectedSuccessResult) } }

        val response = useCase.getCities()

        var success: CitiesResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedSuccessResult, success!!)
        coVerify(exactly = 1) { repository.getCities() }
    }

    @Test
    fun `test getCities with failure response then return error`() = runTest {
        coEvery { repository.getCities() } answers { flow { throw expectedFailureResult } }
        val response = useCase.getCities()

        var success: CitiesResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedFailureResult.message, error!!.message)
        coVerify(exactly = 1) { repository.getCities() }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
