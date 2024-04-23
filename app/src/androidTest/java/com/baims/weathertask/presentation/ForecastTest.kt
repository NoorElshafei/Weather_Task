package com.baims.weathertask.presentation


import android.widget.ArrayAdapter
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.baims.uitest.TestTags
import com.baims.weathertask.BaseTest
import com.baims.weathertask.utils.withTag
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import java.util.function.Predicate.not


class ForecastTest : BaseTest() {

    @Test
    override fun startTest() {
        Thread.sleep(2000)
        onView(withTag(TestTags.ForecastFragment.LAYOUT)).check(matches(isDisplayed()))

        onView(withTag(TestTags.ForecastFragment.DROPDOWN)).check(matches(isDisplayed()))
        // Click dropdown menu
        onView(withTag(TestTags.ForecastFragment.DROPDOWN)).perform(click())

        // Select a city from the dropdown
        onData(anything()).atPosition(0).inRoot(RootMatchers.isPlatformPopup()).perform(click())

        onView(withTag(TestTags.ForecastFragment.SEARCH)).perform(click())
        Thread.sleep(3000)
        onView(withTag(TestTags.ForecastFragment.RV)).check(matches(isDisplayed()))

    }

}