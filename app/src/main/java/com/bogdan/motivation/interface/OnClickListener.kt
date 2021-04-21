package com.bogdan.motivation.`interface`

interface OnClickListener {
    fun onFavoriteClickListener(isFavorite: Boolean, quote: String)
    fun onShareClickListener(quote: String, author: String)
}