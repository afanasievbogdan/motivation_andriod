package com.bogdan.motivation.di.modules

import android.app.Application
import androidx.room.Room
import com.bogdan.motivation.data.dao.QuoteDao
import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.helpers.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
open class DBModule @Inject constructor(private val application: Application) {

    @Provides
    @Singleton
    open fun providesApplicationDatabase(): ApplicationDatabase {
        return Room.databaseBuilder(application, ApplicationDatabase::class.java, Constants.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    open fun providesQuoteDao(applicationDatabase: ApplicationDatabase): QuoteDao = applicationDatabase.quoteDao()

    @Provides
    @Singleton
    open fun providesNotificationDao(applicationDatabase: ApplicationDatabase) = applicationDatabase.notificationDao()

    @Provides
    @Singleton
    open fun providesUtilsDao(applicationDatabase: ApplicationDatabase) = applicationDatabase.utilsDao()

    @Provides
    @Singleton
    open fun providesStyleDao(applicationDatabase: ApplicationDatabase) = applicationDatabase.styleDao()
}