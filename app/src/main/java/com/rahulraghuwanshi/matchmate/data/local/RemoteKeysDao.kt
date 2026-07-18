package com.rahulraghuwanshi.matchmate.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahulraghuwanshi.matchmate.data.local.model.RemoteKeys

/**
 * When remote keys are not directly associated with list items,
 * it is best to store them in a separate table in the local database.
 * While this can be done in the UserDetails table, creating a new table for the next and previous remote keys
 * associated with a UserDetails allows us to have a better separation of concerns.
 */
@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From remote_key Where user_id = :uuId")
    suspend fun getRemoteKeyByUserID(uuId: String): RemoteKeys?

    @Query("Delete From remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}