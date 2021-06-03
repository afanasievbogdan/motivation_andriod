package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.helpers.Styles

class StylesRepository {

    lateinit var db: ApplicationDatabase

    suspend fun insertStyle(style: Style) = db.currentStyleDao().insertStyle(style)

    suspend fun getStyle(): Styles = db.currentStyleDao().getStyle()
}