package com.rahulraghuwanshi.matchmate.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails

@Dao
interface MatchMateDao {
    @Query("Select * from userProfileTables ORDER by id")
    fun getAllUsersData(): PagingSource<Int, UserDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(charactersDetailList: List<UserDetails>)

    @Query("DELETE FROM userProfileTables")
    suspend fun clearAllUserDetails()
}