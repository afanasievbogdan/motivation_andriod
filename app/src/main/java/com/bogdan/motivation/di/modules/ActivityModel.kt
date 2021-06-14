package com.bogdan.motivation.di.modules

import com.bogdan.motivation.ui.activity.MainActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModel(private val activity: MainActivity) {

    @Provides
    fun provideActivity(): MainActivity = activity
}