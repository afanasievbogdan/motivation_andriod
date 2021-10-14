package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.UserDao
import com.bogdan.motivation.data.entities.local.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun getUser() = userDao.getUser()

    suspend fun getUserName() = userDao.getUser().name
}