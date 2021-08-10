package com.bogdan.motivation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogdan.motivation.data.dao.*
import com.bogdan.motivation.data.entities.local.*
import com.bogdan.motivation.helpers.Constants

@Database(
    entities = [Quote::class, Notification::class, Utils::class, Style::class, Categories::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun notificationDao(): NotificationDao
    abstract fun utilsDao(): UtilsDao
    abstract fun styleDao(): StyleDao
    abstract fun themesDao(): CategoriesDao
}