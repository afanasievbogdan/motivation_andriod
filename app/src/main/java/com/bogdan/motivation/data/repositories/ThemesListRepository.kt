package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.entities.local.PickedThemes
import com.bogdan.motivation.data.entities.local.PickedThemesList

class ThemesListRepository {

    fun getThemeList(): PickedThemesList {
        return PickedThemesList(
            listOf(
                PickedThemes("Letting go"),
                PickedThemes("Happiness"),
                PickedThemes("Physical Health"),
                PickedThemes("Self-esteem"),
                PickedThemes("Faith & Spirituality"),
                PickedThemes("Stress & Anxiety"),
                PickedThemes("Achieving goals"),
                PickedThemes("Relationships")
            )
        )
    }
}