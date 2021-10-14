package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.entities.local.PickedCategories
import com.bogdan.motivation.data.entities.local.PickedCategoriesList

class CategoriesListRepository {

    fun getCategoriesList(): PickedCategoriesList {
        return PickedCategoriesList(
            listOf(
                PickedCategories("Прощение"),
                PickedCategories("Счастье"),
                PickedCategories("Физическое здоровье"),
                PickedCategories("Самооценка"),
                PickedCategories("Вера и Духовность"),
                PickedCategories("Стресс и Волнение"),
                PickedCategories("Достижение целей"),
                PickedCategories("Отношения")
            )
        )
    }
}