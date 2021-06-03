package com.bogdan.motivation.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.Styles

// TODO: 15.05.2021 сделай remote and local entities
@Entity(tableName = Constants.TABLE_STYLE)
data class Style(
    @PrimaryKey
    val id: Long = 1,
    // TODO: 15.05.2021 почему c _style?
    @ColumnInfo(name = "style")
    val style: Styles
)