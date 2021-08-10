package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.CategoriesDao
import com.bogdan.motivation.data.entities.local.Categories
import javax.inject.Inject
import com.bogdan.motivation.helpers.Categories as CategoriesEnum

class CategoriesRepository @Inject constructor(private val categoriesDao: CategoriesDao) {

    suspend fun insertCategory(categories: Categories) = categoriesDao.insertCategory(categories)

    suspend fun getCategory(): List<CategoriesEnum> = categoriesDao.getCategory()
}