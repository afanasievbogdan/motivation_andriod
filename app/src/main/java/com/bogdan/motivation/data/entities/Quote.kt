package com.bogdan.motivation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "_quote")
    val quote: String,
    @ColumnInfo(name = "_author")
    val author: String,
    @ColumnInfo(name = "_favorite")
    var isFavorite: Boolean,
    @ColumnInfo(name = "_theme")
    var theme: Themes
)