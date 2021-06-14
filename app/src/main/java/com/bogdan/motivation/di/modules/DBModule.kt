package com.bogdan.motivation.di.modules

import android.app.Application
import androidx.room.Room
import com.bogdan.motivation.helpers.Constants
import dagger.Module

@Module
class DBModule(private val application: Application) {

    init {
        val applicationDatabase =
            Room.databaseBuilder(application, ApplicationDatabase::class.java, Constants.DATABASE_NAME).build()
    }
}