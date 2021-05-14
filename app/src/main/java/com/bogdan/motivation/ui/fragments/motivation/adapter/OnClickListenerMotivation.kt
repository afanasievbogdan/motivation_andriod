package com.bogdan.motivation.ui.fragments.motivation.adapter

import com.bogdan.motivation.data.entities.Quote

interface OnClickListenerMotivation {
    fun onFavoriteClickListener(quote: Quote)
    fun onShareClickListener(quote: Quote)
//    fun onFavoriteClickListener(quote: String, isFavorite: Boolean)
//    fun onShareClickListener(quote: String, author: String)
}