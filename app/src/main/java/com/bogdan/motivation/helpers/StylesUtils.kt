package com.bogdan.motivation.helpers

import android.app.Activity
import android.content.Intent
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.Styles

object StylesUtils {

    fun changeToStyle(activity: Activity, style: Styles): Styles {
        activity.finish()
        activity.startActivity(Intent(activity, activity.javaClass))
        activity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)
        return style
    }

    fun onActivityCreateSetStyle(activity: Activity, style: Styles) {
        when (style) {
            Styles.DARK -> activity.setTheme(R.style.Theme_Motivation)
            Styles.LIGHT -> activity.setTheme(R.style.LightMode)
            Styles.BLUE -> activity.setTheme(R.style.BlueMode)
            Styles.MIX -> activity.setTheme(R.style.MixMode)
        }
    }
}