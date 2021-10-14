package com.bogdan.motivation.data.entities.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.TABLE_USER)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 1,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "sex")
    val sex: String,
    @ColumnInfo(name = "login")
    val login: String? = null,
    @ColumnInfo(name = "password")
    val password: String? = null
) : Parcelable
