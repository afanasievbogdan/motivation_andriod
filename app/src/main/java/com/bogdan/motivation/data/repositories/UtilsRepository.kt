package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.local.Utils

class UtilsRepository {

    lateinit var db: ApplicationDatabase

    suspend fun insertPermissions(permission: Utils) = db.utilsDao().insertUtils(permission)

    suspend fun updatePermissions(permission: Utils) = db.utilsDao().updateUtils(permission)

    suspend fun getPermissions(): Utils = db.utilsDao().getUtils()
}