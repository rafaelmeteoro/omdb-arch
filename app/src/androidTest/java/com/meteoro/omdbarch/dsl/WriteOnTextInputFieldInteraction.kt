package com.meteoro.omdbarch.dsl

import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo

class WriteOnTextInputFieldInteraction(private val textFieldId: Int) {

    infix fun received(text: String) = writeTo(textFieldId, text)
}