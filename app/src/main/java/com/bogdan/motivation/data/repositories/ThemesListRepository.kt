package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.entities.local.PickedThemes

class ThemesListRepository {

    fun getThemeList() = listOf(
        PickedThemes("Letting go"),
        PickedThemes("Happiness"),
        PickedThemes("Physical Health"),
        PickedThemes("Self-esteem"),
        PickedThemes("Faith & Spirituality"),
        PickedThemes("Stress & Anxiety"),
        PickedThemes("Achieving goals"),
        PickedThemes("Relationships")
    )
}