package com.bogdan.motivation.data.repositories

import android.content.Context
import com.bogdan.motivation.data.db.DBManager
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.helpers.StylesUtils

class DBRepository {

    private lateinit var dbManager: DBManager

    fun connectToDb(context: Context) {
        dbManager = DBManager(context)
        dbManager.openDb()
    }

    fun getDbInstance(): DBManager = dbManager

    fun insertStyleToStylesDb(style: String) {
        dbManager.insertStyleToStylesDb(style)
    }

    fun insetToPermissionsDb(
        isSettingsPassed: String,
        isPopupPassed: String,
        isFavoriteOpen: String
    ) {
        dbManager.insetToPermissionsDb(isSettingsPassed, isPopupPassed, isFavoriteOpen)
    }

    fun insertFavoriteKeyToQuotesDb(favorite: String, quote: String) {
        dbManager.insertFavoriteKeyToQuotesDb(favorite, quote)
    }

    fun insetToNotificationsDb(quantity: String, startTime: String, endTime: String) {
        dbManager.insetToNotificationsDb(quantity, startTime, endTime)
    }

    fun insetToThemesDb(theme: String) {
        dbManager.insetToThemesDb(theme)
    }

    fun readStyleFromStylesDb(): StylesUtils.Styles {
        return dbManager.readStyleFromStylesDb()
    }

    suspend fun readFromThemesDb(): String {
        return dbManager.readFromThemesDb()
    }

    fun readFavouriteQuoteFromQuotesDb(): ArrayList<Quote> {
        return dbManager.readFavouriteQuoteFromQuotesDb()
    }

    fun readSettingsFromPermissionsDb(): String {
        return dbManager.readSettingsFromPermissionsDb()
    }

    fun readPopupFromPermissionsDb(): String {
        return dbManager.readPopupFromPermissionsDb()
    }

    fun readQuantityFromNotificationsDb(): String {
        return dbManager.readQuantityFromNotificationsDb()
    }

    fun readStartTimeFromNotificationsDb(): String {
        return dbManager.readStartTimeFromNotificationsDb()
    }

    fun readEndTimeFromNotificationsDb(): String {
        return dbManager.readEndTimeFromNotificationsDb()
    }

    fun readFavoriteOpenFromPermissionDb(): String {
        return dbManager.readFavoriteOpenFromPermissionDb()
    }

    fun readAllQuotesFromQuotesDb(): ArrayList<Quote> {
        return dbManager.readAllQuotesFromQuotesDb()
    }
}