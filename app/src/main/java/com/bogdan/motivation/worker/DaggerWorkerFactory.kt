package com.bogdan.motivation.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.bogdan.motivation.di.components.AppComponent
import com.bogdan.motivation.di.components.WorkerMap

class DaggerWorkerFactory(private val appComponent: AppComponent) : WorkerFactory() {

    private fun createWorker(workerClassName: String, workers: WorkerMap): ListenableWorker? = try {

        val workerClass = Class.forName(workerClassName).asSubclass(Worker::class.java)
        var provider = workers[workerClass]
        if (provider == null) {
            for ((key, value) in workers) {
                if (workerClass.isAssignableFrom(key)) {
                    provider = value
                    break
                }
            }
        }
        if (provider == null)
            throw IllegalArgumentException("no provider found")
        provider.get()
    } catch (th: Throwable) {
        null
    }

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ) = appComponent
        .workerFactoryComponent()
        .setContext(appContext)
        .setParameters(workerParameters)
        .build()
        .workers()
        .let { createWorker(workerClassName, it) }
}