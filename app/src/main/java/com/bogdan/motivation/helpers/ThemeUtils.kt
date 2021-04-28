package com.bogdan.motivation.helpers

import android.app.Activity
import android.content.Intent
import com.bogdan.motivation.R

object ThemeUtils {

    const val DARK = 0
    const val LIGHT = 1
    const val BLUE = 2
    const val MIX = 3

    fun changeToTheme(activity: Activity, theme: Int): Int {
        activity.finish()
        activity.startActivity(Intent(activity, activity.javaClass))
        activity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)
        return theme
    }

    fun onActivityCreateSetTheme(activity: Activity, theme: Int) {
        when (theme) {
            DARK -> activity.setTheme(R.style.Theme_Motivation)
            LIGHT -> activity.setTheme(R.style.LightMode)
            BLUE -> activity.setTheme(R.style.BlueMode)
            MIX -> activity.setTheme(R.style.MixMode)
            else -> activity.setTheme(R.style.Theme_Motivation)
        }
    }
}