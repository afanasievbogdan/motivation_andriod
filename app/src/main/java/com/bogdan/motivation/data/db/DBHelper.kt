package com.bogdan.motivation.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DBConstants.DATABASE_NAME,
        null,
        DBConstants.DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBConstants.CREATE_TABLE_QUOTES)
        db?.execSQL(DBConstants.CREATE_TABLE_PERMISSIONS)
        db?.execSQL(DBConstants.CREATE_TABLE_NOTIFICATIONS)
        db?.execSQL(DBConstants.CREATE_TABLE_THEMES)
        db?.execSQL(DBConstants.CREATE_TABLE_STYLES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DBConstants.DROP_TABLE_QUOTES)
        db?.execSQL(DBConstants.DROP_TABLE_PERMISSIONS)
        db?.execSQL(DBConstants.DROP_TABLE_NOTIFICATIONS)
        db?.execSQL(DBConstants.DROP_TABLE_THEMES)
        db?.execSQL(DBConstants.DROP_TABLE_STYLES)
        onCreate(db)
    }
}
