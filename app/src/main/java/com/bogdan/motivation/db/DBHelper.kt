package com.bogdan.motivation.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//TODO все константы должны быть в отдельном файле - object. В этом случае должен использоваться data class (в роле модели) для работы с БД, ну пока оставь, вернемся позже

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
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DBConstants.DROP_TABLE_QUOTES)
        db?.execSQL(DBConstants.DROP_TABLE_PERMISSIONS)
        db?.execSQL(DBConstants.DROP_TABLE_NOTIFICATIONS)
        db?.execSQL(DBConstants.DROP_TABLE_THEMES)
        onCreate(db)
    }
}
