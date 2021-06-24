package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.UtilsDao
import com.bogdan.motivation.data.entities.local.Utils
import javax.inject.Inject

class UtilsRepository @Inject constructor(private val utilsDao: UtilsDao) {

    suspend fun insertUtils(utils: Utils) = utilsDao.insertUtils(utils)

    suspend fun updateUtils(utils: Utils) = utilsDao.updateUtils(utils)

    suspend fun getUtils(): Utils = utilsDao.getUtils()
}