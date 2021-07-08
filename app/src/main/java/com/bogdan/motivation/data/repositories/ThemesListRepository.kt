package com.bogdan.motivation.data.repositories

import javax.inject.Inject

// TODO Что за пустой инжект // Без него не работает
class ThemesListRepository @Inject constructor() {

    fun getThemeList() = listOf(
        "Letting go",
        "Happiness",
        "Physical Health",
        "Self-esteem",
        "Faith & Spirituality",
        "Stress & Anxiety",
        "Achieving goals",
        "Relationships"
    )
}