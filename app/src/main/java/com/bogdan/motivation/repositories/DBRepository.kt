package com.bogdan.motivation.repositories

import android.content.Context
import com.bogdan.motivation.db.DBManager

class DBRepository {

    lateinit var dbManager: DBManager

    fun connectToDb(context: Context) {
        dbManager = DBManager(context)
        dbManager.openDb()
    }
}