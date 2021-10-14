package com.bogdan.motivation.helpers

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment

fun View.playAnimationWithOffset(
    @AnimRes animResId: Int,
    startOffset: Long
) = with(AnimationUtils.loadAnimation(context, animResId)) {
    this.startOffset = startOffset
    startAnimation(this)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}