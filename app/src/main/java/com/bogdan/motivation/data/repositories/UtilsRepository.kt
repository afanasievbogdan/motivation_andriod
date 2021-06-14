package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.local.Utils

class UtilsRepository {

    lateinit var db: ApplicationDatabase

    suspend fun insertUtils(utils: Utils) = db.utilsDao().insertUtils(utils)

    suspend fun updateUtils(utils: Utils) = db.utilsDao().updateUtils(utils)

    suspend fun getUtils(): Utils = db.utilsDao().getUtils()
}