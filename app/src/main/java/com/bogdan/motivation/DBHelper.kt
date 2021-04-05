package com.bogdan.motivation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "QuoteDB"
const val TABLE_ALL_QUOTES = "allQuotes"
const val TABLE_PERMISSIONS = "permission"

const val KEY_ID = "_id"

const val KEY_QUOTE = "_quote"
const val KEY_AUTHOR = "_author"
const val KEY_FAVORITE = "_favorite"

const val KEY_SETTING_COMPLETE = "_settingComplete"
const val KEY_POPUP_COMPLETE = "_popupComplete"

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_ALL_QUOTES ($KEY_ID integer PRIMARY KEY, $KEY_QUOTE text UNIQUE, $KEY_AUTHOR text, $KEY_FAVORITE text)")
        db?.execSQL("CREATE TABLE $TABLE_PERMISSIONS ($KEY_ID integer PRIMARY KEY, $KEY_SETTING_COMPLETE text, $KEY_POPUP_COMPLETE text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ALL_QUOTES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERMISSIONS")
        onCreate(db)
    }

}
