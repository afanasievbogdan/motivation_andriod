package com.bogdan.motivation.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.helpers.StylesUtils
import kotlin.random.Random

class DBManager(context: Context) {

    private val mDBHelper = DBHelper(context)
    private lateinit var db: SQLiteDatabase

    fun openDb() {
        db = mDBHelper.writableDatabase
    }

    fun insetToQuotesDb(quote: String, author: String, favourite: String) {
        val values = ContentValues().apply {
            put(DBConstants.KEY_QUOTE, quote)
            put(DBConstants.KEY_AUTHOR, author)
            put(DBConstants.KEY_FAVORITE, favourite)
        }
        db.insertWithOnConflict(
            DBConstants.TABLE_QUOTES,
            null,
            values,
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun readAllQuotesFromQuotesDb(): ArrayList<Quote> {
        val quotesList = ArrayList<Quote>()

        val cursor = db.rawQuery(
            "SELECT * FROM ${DBConstants.TABLE_QUOTES}",
            null
        )

        with(cursor) {
            if (moveToFirst()) {
                val quoteIndex = getColumnIndex(DBConstants.KEY_QUOTE)
                val authorIndex = getColumnIndex(DBConstants.KEY_AUTHOR)
                val favoriteIndex = getColumnIndex(DBConstants.KEY_FAVORITE)
                do {
                    val quoteElement =
                        Quote(
                            getString(quoteIndex),
                            getString(authorIndex),
                            getString(favoriteIndex)
                        )
                    quotesList.add(quoteElement)
                } while (moveToNext())
            }
            close()
        }

        return quotesList
    }

    fun readFavouriteQuoteFromQuotesDb(): ArrayList<Quote> {
        val quotesList = ArrayList<Quote>()

        val cursor = db.rawQuery(
            "SELECT * FROM ${DBConstants.TABLE_QUOTES}",
            null
        )

        with(cursor) {
            if (moveToFirst()) {
                val quoteIndex = getColumnIndex(DBConstants.KEY_QUOTE)
                val authorIndex = getColumnIndex(DBConstants.KEY_AUTHOR)
                val favoriteIndex = cursor.getColumnIndex(DBConstants.KEY_FAVORITE)
                do {
                    if (cursor.getString(favoriteIndex) == "1") {
                        val quoteElement =
                            Quote(
                                cursor.getString(quoteIndex),
                                cursor.getString(authorIndex),
                                cursor.getString(favoriteIndex)
                            )
                        quotesList.add(quoteElement)
                    }
                } while (cursor.moveToNext())
            }
            close()
        }

        return quotesList
    }

    fun readRandomQuoteFromQuotesDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${DBConstants.TABLE_QUOTES}", null
        )

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()

        val mRandom = Random.nextInt(count)

        val cursor1: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_QUOTE}, ${DBConstants.KEY_AUTHOR} " +
                "FROM ${DBConstants.TABLE_QUOTES} " +
                "WHERE ${DBConstants.KEY_ID} = $mRandom",
            null
        )

        var text =
            "Мы сами должны стать теми переменами, которые хотим видеть в мире. Махатма Ганди"
        if (cursor1.moveToFirst()) {
            val quote = cursor1.getString(0)
            val author = cursor1.getString(1)
            text = "$quote $author"
        }

        cursor1.close()
        return text
    }

    fun insertFavoriteKeyToQuotesDb(isFavorite: String, item: String) {
        val strSQL = "UPDATE ${DBConstants.TABLE_QUOTES} " +
            "SET ${DBConstants.KEY_FAVORITE} = $isFavorite " +
            "WHERE ${DBConstants.KEY_QUOTE} = '$item'"

        db.execSQL(strSQL)
    }

    fun insetToPermissionsDb(
        isSettingsPassed: String,
        isPopupPassed: String,
        isFavoriteOpen: String
    ) {
        val values = ContentValues().apply {
            put(DBConstants.KEY_ID, 1)
            put(DBConstants.KEY_SETTING_PASSED, isSettingsPassed)
            put(DBConstants.KEY_POPUP_PASSED, isPopupPassed)
            put(DBConstants.KEY_FAVORITE_OPEN, isFavoriteOpen)
        }
        db.insertWithOnConflict(
            DBConstants.TABLE_PERMISSIONS,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun readSettingsFromPermissionsDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_SETTING_PASSED} " +
                "FROM ${DBConstants.TABLE_PERMISSIONS} " +
                "WHERE ${DBConstants.KEY_ID} = 1",
            null
        )

        var isPermitted = "0"
        if (cursor.moveToFirst()) {
            isPermitted = cursor.getString(0)
        }

        cursor.close()
        return isPermitted
    }

    fun readPopupFromPermissionsDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_POPUP_PASSED} " +
                "FROM ${DBConstants.TABLE_PERMISSIONS} " +
                "WHERE ${DBConstants.KEY_ID} = 1",
            null
        )

        var isPermitted = "0"
        if (cursor.moveToFirst()) {
            isPermitted = cursor.getString(0)
        }

        cursor.close()
        return isPermitted
    }

    fun readFavoriteOpenFromPermissionDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_FAVORITE_OPEN} " +
                "FROM ${DBConstants.TABLE_PERMISSIONS} " +
                "WHERE ${DBConstants.KEY_ID} = 1",
            null
        )

        var isPermitted = "0"
        if (cursor.moveToFirst()) {
            isPermitted = cursor.getString(0)
        }

        cursor.close()
        return isPermitted
    }

    fun insetToNotificationsDb(quantity: String, startTime: String, endTime: String) {
        val values = ContentValues().apply {
            put(DBConstants.KEY_ID, 1)
            put(DBConstants.KEY_QUANTITY, quantity)
            put(DBConstants.KEY_START_TIME, startTime)
            put(DBConstants.KEY_END_TIME, endTime)
        }
        db.insertWithOnConflict(
            DBConstants.TABLE_NOTIFICATIONS,
            null,
            values,
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun readQuantityFromNotificationsDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_QUANTITY} FROM " +
                "${DBConstants.TABLE_NOTIFICATIONS} WHERE " +
                "${DBConstants.KEY_ID} = 1",
            null
        )

        var notifQuantity = "0"
        if (cursor.moveToFirst()) {
            notifQuantity = cursor.getString(0)
        }

        cursor.close()
        return notifQuantity
    }

    fun readStartTimeFromNotificationsDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_START_TIME} FROM " +
                "${DBConstants.TABLE_NOTIFICATIONS} WHERE " +
                "${DBConstants.KEY_ID} = 1",
            null
        )

        var startTime = "0"
        if (cursor.moveToFirst()) {
            startTime = cursor.getString(0)
        }

        cursor.close()
        return startTime
    }

    fun readEndTimeFromNotificationsDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_END_TIME} FROM " +
                "${DBConstants.TABLE_NOTIFICATIONS} WHERE " +
                "${DBConstants.KEY_ID} = 1",
            null
        )

        var endTime = "0"
        if (cursor.moveToFirst()) {
            endTime = cursor.getString(0)
        }

        cursor.close()
        return endTime
    }

    fun insetToThemesDb(theme: String) {
        val values = ContentValues().apply {
            put(DBConstants.KEY_ID, 1)
            put(DBConstants.KEY_LETTING_GO, theme)
            put(DBConstants.KEY_FAITH_SPIRITUALITY, "0")
            put(DBConstants.KEY_HAPPINESS, "0")
            put(DBConstants.KEY_STRESS_ANXIETY, "0")
            put(DBConstants.KEY_PHYSICAL_HEALTH, "0")
            put(DBConstants.KEY_ACHIEVING_GOALS, "0")
            put(DBConstants.KEY_SELF_ESTEEM, "0")
            put(DBConstants.KEY_RELATIONSHIP, "0")
        }
        db.insertWithOnConflict(
            DBConstants.TABLE_THEMES,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun readFromThemesDb(): String {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_LETTING_GO} FROM " +
                "${DBConstants.TABLE_THEMES} WHERE " +
                "${DBConstants.KEY_ID} = 1",
            null
        )

        var lettingGo = "0"
        if (cursor.moveToFirst()) {
            lettingGo = cursor.getString(0)
        }

        cursor.close()
        return lettingGo
    }

    fun insertStyleToStylesDb(style: String) {
        val values = ContentValues().apply {
            put(DBConstants.KEY_ID, 1)
            put(DBConstants.KEY_STYLE, style)
        }
        db.insertWithOnConflict(
            DBConstants.TABLE_STYLES,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun readStyleFromStylesDb(): StylesUtils.Styles {
        val cursor: Cursor = db.rawQuery(
            "SELECT ${DBConstants.KEY_STYLE} FROM " +
                "${DBConstants.TABLE_STYLES} WHERE " +
                "${DBConstants.KEY_ID} = 1",
            null
        )

        var styleName = "DARK"
        if (cursor.moveToFirst()) {
            styleName = cursor.getString(0)
        }

        cursor.close()
        return StylesUtils.Styles.valueOf(styleName)
    }
}
