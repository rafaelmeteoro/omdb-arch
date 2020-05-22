package com.meteoro.omdbarch.dsl

import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions.assertHasDrawable

class ErrorStateImageChecker(private val imageId: Int) {

    infix fun shows(image: ErrorImage) = assertHasDrawable(imageId, image.resource)
}
