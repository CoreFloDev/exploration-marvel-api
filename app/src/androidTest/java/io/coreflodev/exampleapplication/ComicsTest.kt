package io.coreflodev.exampleapplication

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.coreflodev.exampleapplication.core.HttpServerRule
import io.coreflodev.exampleapplication.comics.ComicsActivity
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicsTest {

    private val activity = ActivityTestRule(ComicsActivity::class.java, false, false)
    private val httpServerRule = HttpServerRule()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(activity)
        .around(httpServerRule)


    @Test
    fun givenDisplayStateReceived_WhenEverythingIsFine_thenDisplayStateIsShowed() {
        val expectedMessage = "a message"

        httpServerRule.mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(
                    """
  [{
    "userId": 1,
    "id": 1,
    "title": "title 1",
    "body": "$expectedMessage"
  },
  {
    "userId": 1,
    "id": 2,
    "title": "title 2",
    "body": "this is an other body"
  }]
            """.trimIndent()
                )
        )

        activity.launchActivity(Intent())


        onView(withText(expectedMessage)).check(matches(isDisplayed()))
    }
}
