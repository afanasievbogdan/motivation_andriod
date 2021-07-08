package com.bogdan.motivation.di

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.bogdan.motivation.di.components.AppComponent
import com.bogdan.motivation.di.components.DaggerAppComponent
import com.bogdan.motivation.di.modules.ApiModule
import com.bogdan.motivation.di.modules.DBModule
import com.bogdan.motivation.worker.DaggerWorkerFactory

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .dBModule(DBModule(this))
            .build()

        val configuration = Configuration.Builder()
            .setWorkerFactory(DaggerWorkerFactory(appComponent))
            .build()
        WorkManager.initialize(this, configuration)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}