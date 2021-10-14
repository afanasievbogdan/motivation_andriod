package com.bogdan.motivation.data.entities.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Categories
import com.bogdan.motivation.helpers.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.TABLE_QUOTES)
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "quote")
    val quote: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "comments")
    var comments: MutableList<String>,
    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean,
    @ColumnInfo(name = "theme")
    var theme: Categories
) : Parcelable