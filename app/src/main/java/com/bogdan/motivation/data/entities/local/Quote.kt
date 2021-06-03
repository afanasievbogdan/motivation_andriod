package com.bogdan.motivation.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.Themes

@Entity(tableName = Constants.TABLE_QUOTES)
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "quote")
    val quote: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean,
    @ColumnInfo(name = "theme")
    var theme: Themes
)