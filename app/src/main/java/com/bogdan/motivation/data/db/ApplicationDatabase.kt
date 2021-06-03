package com.bogdan.motivation.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun notificationDao(): NotificationDao
    abstract fun utilsDao(): UtilsDao
    abstract fun currentStyleDao(): StyleDao

    companion object {
        // TODO: 15.05.2021 почему вар капсом? 
        @Volatile
        private var instance: ApplicationDatabase? = null

        // TODO: 15.05.2021 вынеси название дб в константы
        fun getDB(context: Context): ApplicationDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                this.instance = instance
                return instance
            }
        }
    }
}