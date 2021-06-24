package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.StyleDao
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.helpers.Styles
import javax.inject.Inject

class StylesRepository @Inject constructor(private val styleDao: StyleDao) {

    suspend fun insertStyle(style: Style) = styleDao.insertStyle(style)

    suspend fun getStyle(): Styles = styleDao.getStyle()
}