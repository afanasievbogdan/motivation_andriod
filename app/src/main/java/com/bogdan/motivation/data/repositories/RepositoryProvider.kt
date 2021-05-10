package com.bogdan.motivation.data.repositories

object RepositoryProvider {
    val quotesRepository = QuotesApiRepository()
    val themesRepository = ThemesRepository()
    val dbRepository = DBRepository()
}