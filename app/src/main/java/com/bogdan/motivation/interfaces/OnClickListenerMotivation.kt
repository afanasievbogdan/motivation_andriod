package com.bogdan.motivation.interfaces

interface OnClickListenerMotivation {
    fun onFavoriteClickListener(isFavorite: Boolean, quote: String)
    fun onShareClickListener(quote: String, author: String)
}