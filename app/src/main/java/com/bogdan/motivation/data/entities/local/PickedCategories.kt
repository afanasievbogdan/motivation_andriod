package com.bogdan.motivation.data.entities.local

data class PickedCategories(val name: String, var isPicked: Boolean = false)

data class PickedCategoriesList(val pickedCategoriesList: List<PickedCategories>)
