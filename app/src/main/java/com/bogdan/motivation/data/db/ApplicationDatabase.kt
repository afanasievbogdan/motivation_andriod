package com.bogdan.motivation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bogdan.motivation.data.dao.*
import com.bogdan.motivation.data.entities.local.*
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.Converters

@Database(
    entities = [Quote::class, Notification::class, Utils::class, Style::class, Categories::class, User::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun notificationDao(): NotificationDao
    abstract fun utilsDao(): UtilsDao
    abstract fun styleDao(): StyleDao
    abstract fun themesDao(): CategoriesDao
    abstract fun userDao(): UserDao
}