package com.bogdan.motivation.di.modules

import androidx.work.Worker
import com.bogdan.motivation.helpers.WorkerKey
import com.bogdan.motivation.worker.NotificationsWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(NotificationsWorker::class)
    fun bindNotificationsWorker(worker: NotificationsWorker): Worker
}