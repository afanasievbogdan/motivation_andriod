package com.bogdan.motivation.db

object DBConstants {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "QuoteDB.db"

    const val KEY_ID = "_id"

    const val TABLE_QUOTES = "Quotes"
    const val KEY_QUOTE = "_quote"
    const val KEY_AUTHOR = "_author"
    const val KEY_FAVORITE = "_favorite"

    const val CREATE_TABLE_QUOTES = "CREATE TABLE IF NOT EXISTS $TABLE_QUOTES (" +
            "$KEY_ID integer PRIMARY KEY, " +
            "$KEY_QUOTE text UNIQUE, " +
            "$KEY_AUTHOR text, " +
            "$KEY_FAVORITE text)"

    const val DROP_TABLE_QUOTES = "DROP TABLE IF EXISTS $TABLE_QUOTES"

    const val TABLE_PERMISSIONS = "Permission"
    const val KEY_SETTING_PASSED = "_settingComplete"
    const val KEY_POPUP_PASSED = "_popupComplete"

    const val CREATE_TABLE_PERMISSIONS = "CREATE TABLE $TABLE_PERMISSIONS (" +
            "$KEY_ID integer PRIMARY KEY, " +
            "$KEY_SETTING_PASSED text, " +
            "$KEY_POPUP_PASSED text)"

    const val DROP_TABLE_PERMISSIONS = "DROP TABLE IF EXISTS $TABLE_PERMISSIONS"

    const val TABLE_NOTIFICATIONS = "Notifications"
    const val KEY_START_TIME = "_startTime"
    const val KEY_END_TIME = "_endTime"
    const val KEY_QUANTITY = "_quantity"

    const val CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE $TABLE_NOTIFICATIONS (" +
            "$KEY_ID integer PRIMARY KEY, " +
            "$KEY_QUANTITY text, " +
            "$KEY_START_TIME text, " +
            "$KEY_END_TIME text)"

    const val DROP_TABLE_NOTIFICATIONS = "DROP TABLE IF EXISTS $TABLE_NOTIFICATIONS"

    const val TABLE_THEMES = "Themes"
    const val KEY_LETTING_GO = "_lettingGo"
    const val KEY_FAITH_SPIRITUALITY = "_faithSpirituality"
    const val KEY_HAPPINESS = "_happiness"
    const val KEY_STRESS_ANXIETY = "_stressAnxiety"
    const val KEY_PHYSICAL_HEALTH = "_physicalHealth"
    const val KEY_ACHIEVING_GOALS = "_achievingGoals"
    const val KEY_SELF_ESTEEM = "_selfEsteem"
    const val KEY_RELATIONSHIP = "_relationShip"

    const val CREATE_TABLE_THEMES = "CREATE TABLE $TABLE_THEMES (" +
            "$KEY_ID integer PRIMARY KEY, " +
            "$KEY_LETTING_GO text, " +
            "$KEY_FAITH_SPIRITUALITY text, " +
            "$KEY_HAPPINESS text, " +
            "$KEY_STRESS_ANXIETY text, " +
            "$KEY_PHYSICAL_HEALTH text, " +
            "$KEY_ACHIEVING_GOALS text, " +
            "$KEY_SELF_ESTEEM text, " +
            "$KEY_RELATIONSHIP text)"

    const val DROP_TABLE_THEMES = "DROP TABLE IF EXISTS $TABLE_THEMES"
}