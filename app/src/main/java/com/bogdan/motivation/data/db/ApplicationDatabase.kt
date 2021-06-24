package com.bogdan.motivation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogdan.motivation.data.dao.NotificationDao
import com.bogdan.motivation.data.dao.QuoteDao
import com.bogdan.motivation.data.dao.StyleDao
import com.bogdan.motivation.data.dao.UtilsDao
import com.bogdan.motivation.data.entities.local.Notification
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.helpers.Constants

@Database(
    entities = [Quote::class, Notification::class, Utils::class, Style::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun notificationDao(): NotificationDao
    abstract fun utilsDao(): UtilsDao
    abstract fun styleDao(): StyleDao
}