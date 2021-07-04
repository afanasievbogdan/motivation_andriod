package com.bogdan.motivation.di.modules.worker

import androidx.work.WorkerFactory
import com.bogdan.motivation.helpers.WorkerKey
import com.bogdan.motivation.worker.ChildWorkerFactory
import com.bogdan.motivation.worker.NotificationsWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NotificationsWorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(NotificationsWorker::class)
    abstract fun bindNotificationsWorker(factory: NotificationsWorker.Factory): ChildWorkerFactory

    @Binds
    abstract fun bindWorkerFactory(factory: NotificationsWorkerFactory): WorkerFactory
}