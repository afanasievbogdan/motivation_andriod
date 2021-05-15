package com.bogdan.motivation.helpers

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

// TODO: 15.05.2021 поменяй название файла
fun View.playAnimationWithOffset(
    @AnimRes animResId: Int,
    startOffset: Long
) = with(AnimationUtils.loadAnimation(context, animResId)) {
    this.startOffset = startOffset
    startAnimation(this)
}