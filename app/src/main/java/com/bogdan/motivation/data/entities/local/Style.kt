package com.bogdan.motivation.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.Styles

@Entity(tableName = Constants.TABLE_STYLE)
data class Style(
    @PrimaryKey
    val id: Long = 1,
    @ColumnInfo(name = "style")
    val style: Styles
)