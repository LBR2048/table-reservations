package com.example.leonardo.waiterapp

import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class MainActivityTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var countingTaskExecutorRule = CountingTaskExecutorRule()

    @Before fun ensurePortraitOrientation() {
        device.setOrientationNatural()
    }

    @Test fun checkCustomersScreenTitle() {
        checkScreenTitle(R.string.customers)

        // Rotate device
        device.setOrientationLeft()

        checkScreenTitle(R.string.customers)
    }

    @Test fun checkCustomersScreenTitleAfterOnBackPressed() {
        checkScreenTitle(R.string.customers)

        // Go to Tables screen and back
        selectRecyclerViewItem(R.id.list, 0)
        device.pressBack()

        checkScreenTitle(R.string.customers)
    }

    @Test fun checkTablesScreenTitle() {
        // Go to Tables screen
        selectRecyclerViewItem(R.id.list, 0)

        checkScreenTitle(R.string.tables)

        // Rotate device
        device.setOrientationLeft()

        checkScreenTitle(R.string.tables)
    }

    private fun selectRecyclerViewItem(@LayoutRes id: Int, position: Int) {
        onView(withId(id))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    private fun checkScreenTitle(@StringRes stringRes: Int) {
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
                .check(matches(withText(stringRes)))
    }

    @Throws(TimeoutException::class, InterruptedException::class)
    private fun drain() {
        countingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES)
    }
}