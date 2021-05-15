package com.bogdan.motivation.data.repositories

import android.content.Context
import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.*

class DBRepository {
    // TODO: 15.05.2021 раздели на 3 репозитория: quotes, notif, permissions
    private lateinit var db: ApplicationDatabase

    // TODO: 15.05.2021 1 раз инициализируй бд, поменяй название функции
    fun connectToDb(applicationContext: Context) {
        RepositoryProvider.dbRepository.db = ApplicationDatabase.getDB(applicationContext)
    }

    // TODO: 15.05.2021 сделай все через = в 1 строку или все в {}
    fun addAllQuotes(quotes: List<Quote>) {
        db.quoteDao().addAllQuotes(quotes)
    }

    suspend fun readAllQuotes(): List<Quote> =
        db.quoteDao().readAllQuotes()

    suspend fun readFavoriteQuotes(): List<Quote> =
        db.quoteDao().readFavoriteQuotes()

    fun readRandomQuote(): Quote =
        db.quoteDao().readRandomQuote()

    suspend fun updateQuote(quote: String, favorite: Boolean) =
        db.quoteDao().updateQuote(quote, favorite)

    suspend fun saveNotification(notification: Notification) =
        db.notificationDao().saveNotification(notification)

    suspend fun readNotification(): Notification =
        db.notificationDao().readNotification()

    fun readStartTime(): String =
        db.notificationDao().readStartTime()

    fun readEndTime(): String =
        db.notificationDao().readEndTime()

    suspend fun savePermissions(permission: Permissions) =
        db.permissionsDao().savePermissions(permission)

    suspend fun updatePermissions(permission: Permissions) =
        db.permissionsDao().updatePermissions(permission)

    suspend fun readPermissions(): Permissions =
        db.permissionsDao().readPermissions()

    suspend fun saveCurrentStyle(currentStyle: CurrentStyle) =
        db.currentStyleDao().saveCurrentStyle(currentStyle)

    suspend fun readCurrentStyle(): Styles =
        db.currentStyleDao().readCurrentStyle()
}