package com.bogdan.motivation.entities

data class Quote(
    val quote: String,
    val author: String,
    var favorite: String
) {
    var isFavorite = favorite == "1"

    fun changeFavorite(): String {
        favorite = if (favorite == "1") "0" else "1"
        isFavorite = favorite == "1"
        return favorite
    }
}
