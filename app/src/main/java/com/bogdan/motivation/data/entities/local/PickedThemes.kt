package com.bogdan.motivation.data.entities.local

data class PickedThemes(val name: String, val isPicked: Boolean = false)

data class PickedThemesList(val pickedThemesList: List<PickedThemes>)
