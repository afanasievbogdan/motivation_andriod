package com.bogdan.motivation.di.components

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bogdan.motivation.di.modules.WorkerModule
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Provider

typealias WorkerMap = MutableMap<Class<out Worker>, Provider<Worker>>

@Subcomponent(modules = [WorkerModule::class])
interface WorkerFactoryComponent {

    fun workers(): WorkerMap

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setParameters(params: WorkerParameters): Builder

        @BindsInstance
        fun setContext(context: Context): Builder
        fun build(): WorkerFactoryComponent
    }
}