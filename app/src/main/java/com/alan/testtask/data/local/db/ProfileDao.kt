package com.alan.testtask.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alan.testtask.data.local.model.ProfileEntity

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile WHERE phone = :phone LIMIT 1")
    suspend fun getProfileByPhone(phone: String): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profile: ProfileEntity)
}

