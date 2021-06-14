package com.bogdan.motivation.data.repositories

object RepositoryProvider {
    val quotesApiRepository = QuotesApiRepository()
    val themesRepository = ThemesRepository()

    val quotesRepository = QuotesRepository()
    val notificationsRepository = NotificationsRepository()
    val utilsRepository = UtilsRepository()
    val stylesRepository = StylesRepository()
}