package com.bogdan.motivation.di

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.bogdan.motivation.di.components.AppComponent
import com.bogdan.motivation.di.components.DaggerAppComponent
import com.bogdan.motivation.di.modules.ApiModule
import com.bogdan.motivation.di.modules.DBModule
import javax.inject.Inject

class Application : Application() {

//    @Inject
//    lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule())
            .dBModule(DBModule(this))
            .build()

//        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}