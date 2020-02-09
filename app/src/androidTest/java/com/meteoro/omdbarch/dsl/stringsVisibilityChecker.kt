package com.meteoro.omdbarch.dsl

import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed

infix fun String.shouldBe(visibility: Visibility) =
    when (visibility) {
        Visibility.DISPLAYED -> assertDisplayed(this)
        Visibility.HIDDEN -> assertNotDisplayed(this)
    }

class CheckStringVisibility(private val messageId: Int) {

    infix fun shouldBe(visibility: Visibility) =
        when (visibility) {
            Visibility.DISPLAYED -> assertDisplayed(messageId)
            Visibility.HIDDEN -> assertNotDisplayed(messageId)
        }
}