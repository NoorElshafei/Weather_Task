package com.baims.weathertask

import androidx.test.core.app.ActivityScenario
import org.junit.Before
import org.junit.Test

abstract class BaseTest {

    lateinit var scenario: ActivityScenario<MainActivity>
    lateinit var activity: MainActivity

    @Before
    fun setUp() {
        createActivity()
    }

    private fun createActivity() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            activity = it
        }
    }

    @Test
    abstract fun startTest()
}