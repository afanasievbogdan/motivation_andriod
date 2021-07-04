package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.ThemesDao
import com.bogdan.motivation.data.entities.local.Themes
import javax.inject.Inject
import com.bogdan.motivation.helpers.Themes as ThemesEnum

class ThemesRepository @Inject constructor(private val themesDao: ThemesDao) {

    suspend fun insertTheme(themes: Themes) = themesDao.insertTheme(themes)

    suspend fun getTheme(): List<ThemesEnum> = themesDao.getTheme()
}