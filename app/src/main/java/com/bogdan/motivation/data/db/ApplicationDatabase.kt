package com.bogdan.motivation.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bogdan.motivation.data.dao.*
import com.bogdan.motivation.data.entities.CurrentStyle
import com.bogdan.motivation.data.entities.Notification
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.entities.Quote

@Database(
    entities = [Quote::class, Notification::class, Permissions::class, CurrentStyle::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun notificationDao(): NotificationDao
    abstract fun permissionsDao(): PermissionsDao
    abstract fun currentStyleDao(): CurrentStyleDao

    companion object {
        // TODO: 15.05.2021 почему вар капсом? 
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        // TODO: 15.05.2021 вынеси название дб в константы
        fun getDB(context: Context): ApplicationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "MotivationDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}