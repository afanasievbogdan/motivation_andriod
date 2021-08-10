package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.entities.local.PickedCategories
import com.bogdan.motivation.data.entities.local.PickedCategoriesList

class CategoriesListRepository {

    fun getCategoriesList(): PickedCategoriesList {
        return PickedCategoriesList(
            listOf(
                PickedCategories("Letting go"),
                PickedCategories("Happiness"),
                PickedCategories("Physical Health"),
                PickedCategories("Self-esteem"),
                PickedCategories("Faith & Spirituality"),
                PickedCategories("Stress & Anxiety"),
                PickedCategories("Achieving goals"),
                PickedCategories("Relationships")
            )
        )
    }
}