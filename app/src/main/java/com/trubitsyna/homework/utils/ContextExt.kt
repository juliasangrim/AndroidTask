package com.trubitsyna.homework.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToastError(@StringRes resId: Int) {
    Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
}