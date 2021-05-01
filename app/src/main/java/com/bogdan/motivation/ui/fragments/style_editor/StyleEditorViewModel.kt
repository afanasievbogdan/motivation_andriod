package com.bogdan.motivation.ui.fragments.style_editor

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogdan.motivation.helpers.ThemeUtils

class StyleEditorViewModel : ViewModel() {

    val liveData = MutableLiveData<String>()

    init {
        changeTheme(activity = Activity())
    }

    private fun changeTheme(activity: Activity) {
        liveData.value = ThemeUtils.changeToTheme(activity, ThemeUtils.Styles.LIGHT)
    }
}