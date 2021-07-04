package com.bogdan.motivation.data.repositories

import javax.inject.Inject

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