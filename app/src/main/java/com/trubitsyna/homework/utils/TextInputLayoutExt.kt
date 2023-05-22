package com.trubitsyna.homework.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.showError(msg: String) {
    this.error = msg
}

fun TextInputLayout.enable() {
    with(this) {
        isFocusable = true
        isEnabled = true
    }
}

fun TextInputLayout.disable() {
    with(this) {
        isFocusable = false
        isEnabled = false
    }
}

fun TextInputLayout.clearError() {
    with(this) {
        error = null
        isErrorEnabled = false
    }
}