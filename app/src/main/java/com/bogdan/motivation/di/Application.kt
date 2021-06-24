package com.bogdan.motivation.di

import android.app.Application
import com.bogdan.motivation.di.components.AppComponent
import com.bogdan.motivation.di.components.DaggerAppComponent
import com.bogdan.motivation.di.modules.ApiModule
import com.bogdan.motivation.di.modules.DBModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .dBModule(DBModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}