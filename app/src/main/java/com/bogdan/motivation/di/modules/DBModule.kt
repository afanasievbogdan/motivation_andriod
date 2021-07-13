package com.bogdan.motivation.di.modules

import android.app.Application
import androidx.room.Room
import com.bogdan.motivation.data.dao.QuoteDao
import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.repositories.QuotesRepository
import com.bogdan.motivation.data.repositories.ThemesListRepository
import com.bogdan.motivation.helpers.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DBModule @Inject constructor(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplicationDatabase(): ApplicationDatabase {
        return Room.databaseBuilder(application, ApplicationDatabase::class.java, Constants.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun providesQuoteDao(applicationDatabase: ApplicationDatabase): QuoteDao = applicationDatabase.quoteDao()

    @Provides
    @Singleton
    fun providesNotificationDao(applicationDatabase: ApplicationDatabase) = applicationDatabase.notificationDao()

    @Provides
    @Singleton
    fun providesUtilsDao(applicationDatabase: ApplicationDatabase) = applicationDatabase.utilsDao()

    @Provides
    @Singleton
    fun providesStyleDao(applicationDatabase: ApplicationDatabase) = applicationDatabase.styleDao()

    @Provides
    @Singleton
    fun providesThemesDao(applicationDatabase: ApplicationDatabase) = applicationDatabase.themesDao()

    @Provides
    @Singleton
    fun providesQuotesRepository(quoteDao: QuoteDao) = QuotesRepository(quoteDao)

    @Provides
    fun provideThemesListRepository() = ThemesListRepository()
}