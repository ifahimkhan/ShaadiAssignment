package com.fahim.shaadi.data.repository

import androidx.lifecycle.LiveData
import com.fahim.bookapptesting.util.Resource
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.data.model.ApiProfileResponse

interface ProfileInterface {
    suspend fun insertProfile(profileModel: ProfileModel)
    suspend fun deleteProfile(profileModel: ProfileModel)
    suspend fun updateProfile(profileModel: ProfileModel)
    suspend fun getOnlineProfile(count: String): Resource<ApiProfileResponse>
    fun getLocalProfiles(): LiveData<List<ProfileModel>>
}