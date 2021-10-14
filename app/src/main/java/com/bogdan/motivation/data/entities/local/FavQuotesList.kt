package com.bogdan.motivation.data.entities.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavQuotesList(val favQuotes: List<String>) : Parcelable
