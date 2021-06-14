package com.bogdan.motivation.ui

import android.app.Application
import com.bogdan.motivation.di.components.ApplicationComponent
import com.bogdan.motivation.di.components.DaggerApplicationComponent

class MotivationApplication : Application() {

    val applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()
}