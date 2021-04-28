package com.bogdan.motivation.helpers

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View.playAnimation(
    @AnimRes animResId: Int,
    startOffset: Long
) = with(AnimationUtils.loadAnimation(context, animResId)) {
    this.startOffset = startOffset
    startAnimation(this)
}