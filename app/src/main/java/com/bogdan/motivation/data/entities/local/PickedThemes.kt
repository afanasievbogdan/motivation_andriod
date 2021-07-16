package com.bogdan.motivation.data.entities.local

data class PickedThemes(val name: String, var isPicked: Boolean = false)

data class PickedThemesList(val pickedThemesList: List<PickedThemes>)
