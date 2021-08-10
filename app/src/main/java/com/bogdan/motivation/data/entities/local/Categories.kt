package com.bogdan.motivation.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.Categories as CategoriesEnum

@Entity(tableName = Constants.TABLE_CATEGORIES)
data class Categories(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "category")
    val category: CategoriesEnum
)
