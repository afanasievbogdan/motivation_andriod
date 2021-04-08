package com.bogdan.motivation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "QuoteDB"

const val TABLE_ALL_QUOTES = "allQuotes"
const val TABLE_PERMISSIONS = "permission"
const val TABLE_NOTIFICATION = "notifications"
const val TABLE_THEMES = "themes"

const val KEY_ID = "_id"

const val KEY_QUOTE = "_quote"
const val KEY_AUTHOR = "_author"
const val KEY_FAVORITE = "_favorite"

const val KEY_SETTING_COMPLETE = "_settingComplete"
const val KEY_POPUP_COMPLETE = "_popupComplete"

const val KEY_START_TIME = "_startTime"
const val KEY_END_TIME = "_endTime"
const val KEY_QUANTITY = "_quantity"

const val KEY_LETTING_GO = "_lettingGo"
const val KEY_FAITH_SPIRITUALITY = "faithSpirituality"
const val KEY_HAPPINESS = "_happiness"
const val KEY_STRESS_ANXIETY = "stressAnxiety"
const val KEY_PHYSICAL_HEALTH = "physicalHealth"
const val KEY_ACHIEVING_GOALS = "achievingGoals"
const val KEY_SELF_ESTEEM = "selfEsteem"
const val KEY_RELATIONSHIP = "relationShip"

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_ALL_QUOTES ($KEY_ID integer PRIMARY KEY, $KEY_QUOTE text UNIQUE, $KEY_AUTHOR text, $KEY_FAVORITE text)")
        db?.execSQL("CREATE TABLE $TABLE_PERMISSIONS ($KEY_ID integer PRIMARY KEY, $KEY_SETTING_COMPLETE text, $KEY_POPUP_COMPLETE text)")
        db?.execSQL("CREATE TABLE $TABLE_NOTIFICATION ($KEY_ID integer PRIMARY KEY, $KEY_QUANTITY text, $KEY_START_TIME text, $KEY_END_TIME text)")
        db?.execSQL("CREATE TABLE $TABLE_THEMES ($KEY_ID integer PRIMARY KEY, $KEY_LETTING_GO text, $KEY_FAITH_SPIRITUALITY, $KEY_HAPPINESS, $KEY_STRESS_ANXIETY, $KEY_PHYSICAL_HEALTH, $KEY_ACHIEVING_GOALS, $KEY_SELF_ESTEEM, $KEY_RELATIONSHIP)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ALL_QUOTES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERMISSIONS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTIFICATION")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_THEMES")
        onCreate(db)
    }

}
