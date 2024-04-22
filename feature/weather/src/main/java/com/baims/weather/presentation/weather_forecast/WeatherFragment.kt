package com.baims.weather.presentation.weather_forecast

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.baims.feature.weather.R
import com.baims.feature.weather.databinding.FragmentWeatherBinding
import com.baims.ui.extensions.hide
import com.baims.ui.extensions.show
import com.baims.ui.presentation.BaseFragment
import com.baims.uitest.TestTags
import com.baims.utils.extensions.collect
import com.baims.utils.extensions.observe
import com.baims.utils.states.DataState
import com.baims.weather.data.response.City
import com.baims.weather.domain.enums.ForecastValidation
import com.baims.weather.presentation.weather_forecast.adapter.ForecastAdapter
import com.baims.uitest.testTag
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {

    private val viewModel by viewModels<WeatherViewModel>()
    private var selectedCity: City? = City()
    private var dropDownAdapter: ArrayAdapter<String>? = null
    private var cityItems: ArrayList<String> = arrayListOf()


    @Inject
    lateinit var adapter: ForecastAdapter

    override fun bindViews() {

        initUI()
        subscribeOnObservers()
    }

    private fun initUI() {
        binding.forecastRV.adapter = adapter
        viewModel.getCities()
        binding.searchButton.setOnClickListener {
            viewModel.getForecast(selectedCity)
        }
        testTags()
    }


    private fun subscribeOnObservers() {
        observe(viewModel.validationForecast) { showValidation(it) }
        collect(viewModel.citiesDataState) {
            when (it) {
                is DataState.Success -> {
                    hideLoading()
                    initCities(it.data.cities)
                }

                is DataState.Failure -> {
                    hideLoading()
                    //showMessage(it.throwable.getType().getMessage().text ?: "")
                }

                DataState.Loading -> showLoading()
            }
        }
        collect(viewModel.forecastDataState) {
            when (it) {
                is DataState.Success -> {
                    hideLoading()
                    adapter.submitList(it.data.list)
                }

                is DataState.Failure -> {
                    hideLoading()
                    // showMessage(it.throwable.getType().getMessage().text ?: "")
                }

                DataState.Loading -> showLoading()
            }
        }
    }

    private fun initCities(cities: List<City?>?) {
        for (x in cities ?: listOf()) {
            x?.cityNameEn?.let { cityItems.add(it) }
        }
        dropDownAdapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item_city,
            cityItems
        )
        binding.apply {
            autoCompleteTextView.setAdapter(dropDownAdapter)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, position, l ->
                selectedCity = cities?.get(position)
            }
        }
    }

    private fun showValidation(invalidField: Int?) {
        invalidField?.let {
            when (it) {
                ForecastValidation.EMPTY_FORECAST.value -> {
                    showMessage("You must select city")
                }
            }
        }
    }

    private fun hideLoading() {
        binding.loadingLayout.loading.hide()
        // binding.forecastRV.show()
    }

    private fun showLoading() {
        binding.loadingLayout.loading.show()
        // binding.forecastRV.hide()
    }

    private fun testTags() {
        binding.forecastLayout.testTag(TestTags.ForecastFragment.LAYOUT)
        binding.forecastRV.testTag(TestTags.ForecastFragment.RV)
    }

    override fun getLayoutResId(): Int = R.layout.fragment_weather
}
