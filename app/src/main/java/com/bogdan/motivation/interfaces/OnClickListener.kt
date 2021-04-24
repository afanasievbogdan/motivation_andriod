package com.bogdan.motivation.interfaces

interface OnClickListener {
    fun onFavoriteClickListener(isFavorite: Boolean, quote: String)
    fun onShareClickListener(quote: String, author: String)
}