package com.trubitsyna.homework.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.snackbar.Snackbar
import com.trubitsyna.homework.R


fun View.replace(newView: View) {
    this.visibility = View.GONE
    newView.visibility = View.VISIBLE
}

fun View.showError(@StringRes resId: Int) {
    Snackbar.make(this, resId, Snackbar.LENGTH_SHORT)
        .show()
}

fun View.showErrorWithAction(@StringRes resId: Int, action: () -> Unit) {
    Snackbar.make(this, resId, Snackbar.LENGTH_LONG)
        .setAction(R.string.action_text) {
            action.invoke()
        }.show()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.applySystemBarsTopInset() {
    doOnApplyWindowInsets { view, insets, padding ->
        val topPadding = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
        view.updatePadding(
            top = padding.top + topPadding
        )
        insets
    }
}

fun View.doOnApplyWindowInsets(block: (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat) {

    val initialPadding = recordInitialPaddingForView(this)

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }

    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}