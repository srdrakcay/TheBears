package com.serdar.thebears.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serdar.profile.ui.ProfileFragment
import com.serdar.thebears.MainActivity
import com.serdar.thebears.R
import com.serdar.thebears.ui.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testEventFragment() {
        launchFragmentInHiltContainer<ProfileFragment> {}
    }

    @Test
    fun activityIsShownWithoutBottomBar() {
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.bottomNavigation)).check(matches(not(isDisplayed())))
    }

    @Test
    fun activityIsShownWithBottomBar() {
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.bottomNavigation)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun clickTheActivityWindow() {
        onView(withId(R.id.main_layout)).perform(click())
    }


}