package com.bogdan.motivation.ui.fragments.motivation

import com.bogdan.motivation.data.entities.Quote

sealed class MotivationViewState {
    class FavoriteOpenFromPermissionDb(val permissionFavoriteOpen: String) : MotivationViewState()
    class AllQuotesFromQuotesDb(val allQuotes: ArrayList<Quote>) : MotivationViewState()
    class FavouriteQuoteFromQuotesDb(val favouriteQuote: ArrayList<Quote>) : MotivationViewState()
    class PopupFromPermissionsDb(val permissionPopup: String) : MotivationViewState()
}
