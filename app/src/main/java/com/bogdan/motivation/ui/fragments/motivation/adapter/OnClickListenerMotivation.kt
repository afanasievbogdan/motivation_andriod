package com.bogdan.motivation.ui.fragments.motivation.adapter

interface OnClickListenerMotivation {
    fun onFavoriteClickListener(isFavorite: Boolean, quote: String)
    fun onShareClickListener(quote: String, author: String)
}