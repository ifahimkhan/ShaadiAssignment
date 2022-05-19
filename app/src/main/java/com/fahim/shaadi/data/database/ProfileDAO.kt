package com.fahim.shaadi.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profileModel: ProfileModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProfiles(profiles:List<ProfileModel>);

    @Delete
    suspend fun deleteProfile(profileModel: ProfileModel);

    @Update
    suspend fun updateProfile(profileModel: ProfileModel);

    @Query("select * from profiles where isAccepted = -1" )
    fun observeProfile(): LiveData<List<ProfileModel>>

    @Query("select * from profiles where isAccepted = 1")
    fun observeAcceptedProfile(): LiveData<List<ProfileModel>>

    @Query("select * from profiles where isAccepted = 0")
    fun observeDeclinedProfile(): LiveData<List<ProfileModel>>
}