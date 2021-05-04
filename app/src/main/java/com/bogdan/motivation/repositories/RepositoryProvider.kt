package com.bogdan.motivation.repositories

object RepositoryProvider {
    val quotesRepository = QuotesApiRepository()
    val themesRepository = ThemesRepository()
    val dbRepository = DBRepository()
}