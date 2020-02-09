package com.meteoro.omdbarch.dsl

import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed

class ViewVisibilityChecker(private val view: Int) {

    infix fun shouldBe(visibility: Visibility) =
        when (visibility) {
            Visibility.DISPLAYED -> assertDisplayed(view)
            Visibility.HIDDEN -> assertNotDisplayed(view)
        }
}