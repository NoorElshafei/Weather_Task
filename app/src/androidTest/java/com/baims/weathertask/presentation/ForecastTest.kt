package com.baims.weathertask.presentation


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.baims.uitest.TestTags
import com.baims.weathertask.BaseTest
import com.baims.weathertask.utils.withTag
import org.junit.Test


class ForecastTest : BaseTest() {

    @Test
    override fun startTest() {
        Thread.sleep(2000)
        onView(withTag(TestTags.ForecastFragment.LAYOUT)).check(matches(isDisplayed()))
        onView(withTag(TestTags.ForecastFragment.RV)).check(matches(isDisplayed()))
    }

}