package com.adesso.movee.scene.splash

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adesso.movee.R
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun finishCurrentActivity_whenStartingMainActivity() {
        assertTrue(activityTestRule.activity.isFinishing)
    }

    @Test
    fun starMainActivity_whenSplashActivityCreated() {
        Espresso.onView(withId(R.id.main_host_fragment))
            .check(ViewAssertions.matches(isDisplayed()))
    }
}
