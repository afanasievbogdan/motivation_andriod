package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.Permissions

@Dao
interface PermissionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePermissions(permission: Permissions)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePermissions(permission: Permissions)

    @Query("SELECT * FROM Permissions WHERE id = 1")
    suspend fun readPermissions(): Permissions
}