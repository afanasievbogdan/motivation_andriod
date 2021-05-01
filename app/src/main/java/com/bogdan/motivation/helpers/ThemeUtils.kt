package com.bogdan.motivation.helpers

import android.app.Activity
import android.content.Intent
import com.bogdan.motivation.R

object ThemeUtils {

    enum class Styles {
        DARK, LIGHT, BLUE, MIX
    }

    fun changeToTheme(activity: Activity, theme: Styles): String {
        activity.finish()
        activity.startActivity(Intent(activity, activity.javaClass))
        activity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)
        return theme.name
    }

    fun onActivityCreateSetTheme(activity: Activity, theme: Styles) {
        when (theme) {
            Styles.DARK -> activity.setTheme(R.style.Theme_Motivation)
            Styles.LIGHT -> activity.setTheme(R.style.LightMode)
            Styles.BLUE -> activity.setTheme(R.style.BlueMode)
            Styles.MIX -> activity.setTheme(R.style.MixMode)
        }
    }
}