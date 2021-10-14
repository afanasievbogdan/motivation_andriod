package com.bogdan.motivation.ui.fragments.motivation.adapter

import com.bogdan.motivation.data.entities.local.Quote

interface OnClickListenerMotivation {
    fun onFavoriteClickListener(quote: Quote)
    fun onShareClickListener(quote: Quote)
    fun onCommentClickListener(quote: Quote)
    fun changeData()
}